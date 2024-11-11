package com.project.demo.whiskeys.service;

import com.project.demo.whiskeys.entity.WhiskeyList;
import com.project.demo.whiskeys.repository.WhiskeyRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WhiskeyCrawlingService {

    @Autowired
    private WhiskeyRepository whiskeyRepository;

    public void crawl() {
        System.setProperty("webdriver.chrome.driver", System.getenv("CHROME_DRIVER"));

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        WebDriver driver = new ChromeDriver(options);

        try {
            String url = "https://www.lotteon.com/search/render/render.ecn?render=nqapi&platform=pc&collection_id=301&u9=navigate&u8=LM90150163&login=Y&mallId=4";
            driver.get(url);

            WebElement webElement = driver.findElement(By.cssSelector("#content > div > section > div > section.srchResultContentArea > div > div > div.srchResultSortArea > div > div:nth-child(3) > button"));
            webElement.click();

            // 더 넘길 페이지 없을 때까지 반복
            while (true) {
                WebElement whiskeyContainer = driver.findElement(By.cssSelector("div.s-goods-layout.s-goods-layout__list"));

                // 페이지에 항목이 없을 때까지 반복
                for (WebElement whiskey : whiskeyContainer.findElements(By.cssSelector("div.s-goods-grid.s-goods-grid--col-4 > ul > li"))) {

                    // 위스키 이름, 사진 url, 정보 문자열로 저장
                    String name = whiskey.findElement(By.cssSelector("div > div.s-goods__info > a > div.s-goods-title")).getText();
                    String imgUrl = whiskey.findElement(By.cssSelector("div.s-goods__thumbnail > a > div.s-goods-image > img")).getAttribute("src");

                    // 스펙 정보 초기화
                    String category = "Unknown";
                    String abv = "Unknown";
                    String country = "Unknown";
                    String year = "NAS"; // 디폴트값
                    String bottleSize = "Unknown";

                    // 위스키 정보 엘리먼트 크롤링
                    try {
                        WebElement spec = whiskey.findElement(By.className("s-goods-spec__list"));
                        List<WebElement> specValues = spec.findElements(By.tagName("span"));
                        List<WebElement> specKeys = spec.findElements(By.tagName("em"));

                        Map<String, String> specMap = new HashMap<>();
                        for (int i = 0; i < specValues.size(); i++)
                            specMap.put(specKeys.get(i).getText(), specValues.get(i).getText());

                        // 크롤링 정보 매핑
                        if (specMap.containsKey("종류"))
                            category = specMap.get("종류");
                        if (specMap.containsKey("도수"))
                            abv = specMap.get("도수");
                        if (specMap.containsKey("나라"))
                            country = specMap.get("나라");
                        if (specMap.containsKey("년 수"))
                            year = specMap.get("년 수");
                        if (specMap.containsKey("용량"))
                            bottleSize = specMap.get("용량");

                        whiskeyRepository.save(new WhiskeyList(name, imgUrl, category, abv, country, year, bottleSize));
                    } catch (NoSuchElementException ignored) {
                        whiskeyRepository.save(new WhiskeyList(name, imgUrl, category, abv, country, year, bottleSize));
                    }
                }

                // 페이지바 업데이트, 더 넘길 페이지 없을 경우 반복문 탈출
                try {
                    WebElement nextPage = driver.findElement(By.cssSelector("#content > div > section > div > section.srchResultContentArea > div > div > div.s-goods-layout.s-goods-layout__list > div.srchPagination > a.srchPaginationNext"));
                    nextPage.click();
                } catch (NoSuchElementException e) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
