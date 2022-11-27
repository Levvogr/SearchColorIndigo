package parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.WheelInput;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static download.ImageDownloader.downloadImage;

public class ParserColorIndigo {
    public static void ParseColorIndigo() throws IOException, InterruptedException {
        System.setProperty("webdriver.chrome.driver","selenium\\chromedriver.exe");
        WebDriver webDriver=new ChromeDriver();
        //картинки ищутся в яндексе, так как их удобнее от туда скачивать
        webDriver.get("https://yandex.ru/images/search");
        //ввод текста в поисковую строку
        webDriver
                .findElement(By.xpath("/html/body/header/div/div[1]/div[2]/form/div[1]/span/span/input"))
                .sendKeys("Цвет индиго");
        //переход по введенному тексту
        webDriver
                .findElement(By.xpath("/html/body/header/div/div[1]/div[2]/form/div[1]/span/span/input"))
                .sendKeys(Keys.RETURN);

        //чтобы адреса картинок загрузились в страницу html нужно перемотать страничку вниз
        for (int i = 0; i < 20; i++) {
            JavascriptExecutor jse = (JavascriptExecutor)webDriver;
            jse.executeScript("window.scrollBy(0,10000)");
            //пауза необходима, чтобы браузер подгрузил данные
            Thread.sleep(1500);
        }
        //получения списка адресов картинок
        List<String> imgs = Jsoup.parse((webDriver.getPageSource()))
                .getElementsByClass("serp-controller__content")
                .get(0)
                .getElementsByTag("img")
                .stream()
                .map(img -> img.attr("src"))
                .toList();
        for (int i = 0; i < imgs.size(); i++) {
            // Скачивание картинки. Примечание: если в указанном пути не существует
            // указанной папки будет ошибка, в данном случае эта папка images
            downloadImage("images/indigo"+i+".jpg","https:"+imgs.get(i));
            //вывод адреса и номера скачанной картинки
            System.out.println("downloaded img"+i+": https:"+imgs.get(i));
        }

    }
}
