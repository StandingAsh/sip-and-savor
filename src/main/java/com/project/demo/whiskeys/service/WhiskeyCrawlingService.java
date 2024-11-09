package com.project.demo.whiskeys.service;

import com.project.demo.whiskeys.entity.WhiskeyList;
import com.project.demo.whiskeys.repository.WhiskeyRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WhiskeyCrawlingService {

    @Autowired
    private WhiskeyRepository whiskeyRepository;

    public void crawl() {
        System.setProperty("webdriver.chrome.driver", "C:\\demo\\src\\main\\resources\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        WebDriver driver = new ChromeDriver(options);

        try {
            String url = "https://www.lotteon.com/search/render/render.ecn?render=nqapi&platform=pc&collection_id=301&u9=navigate&u8=LM90150163&login=Y&mallId=4";
            driver.get(url);

            WebElement webElement = driver.findElement(By.cssSelector("#content > div > section > div > section.srchResultContentArea > div > div > div.srchResultSortArea > div > div:nth-child(3) > button"));
            webElement.click();

            WebElement whiskeyContainer = driver.findElement(By.cssSelector("div.s-goods-layout.s-goods-layout__list"));

            for (WebElement whiskey : whiskeyContainer.findElements(By.cssSelector("div.s-goods-grid.s-goods-grid--col-4 > ul > li"))) {

                // 보틀 정보 엘레멘트 저장
                WebElement spec = whiskey.findElement(By.className("s-goods-spec__list"));
                List<WebElement> specs = spec.findElements(By.tagName("span"));

                // 패키지, 세트 등을 걸러주기 위한 조건문
                if (specs.size() < 5 || specs.size() > 6) {
                    continue;
                }

                // 위스키 이름, 사진 url, 정보 문자열로 저장
                String name = whiskey.findElement(By.cssSelector("div > div.s-goods__info > a > div.s-goods-title")).getText();
                String imgUrl = whiskey.findElement(By.cssSelector("div.s-goods__thumbnail > a > div.s-goods-image > img")).getAttribute("src");

                String category = specs.get(0).getText();
                String abv = specs.get(1).getText();
                String country = specs.get(2).getText();
                String year = "NAS"; // 디폴트값
                String bottleSize;

                // NAS 제품은 숙성년수 미표기, 따라서 스펙 정보 5개
                if (specs.size() == 5) {
                    bottleSize = specs.get(3).getText();
                } else {
                    year = specs.get(3).getText();
                    bottleSize = specs.get(4).getText();
                }

                whiskeyRepository.save(new WhiskeyList(name, imgUrl, category, abv, country, year, bottleSize));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
