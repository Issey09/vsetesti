package org.example.vsetesti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateTestController {
    @Autowired
    private OpenAiService openAiService;

    
    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/index")
    public String index(@RequestParam String tema) {
        return openAiService.sendRequest("Мне нужен тест по теме" + tema  +
                "Кроме ответов и вопроса не должно быть лишних слов и т.д" +
                "В тесте должно быть 5 вопросов и по 3 варинта ответа на вопрос," +
                "Сначала идет вопрос, потом 2 неправильных варинта ответа и последний правильный" +
                "Пример теста, на основе которого ты будешь мне генерировать тесты:" +
                "Сколько будет 5*3?\n" +
                "10\n" +
                "5\n" +
                "15\n" +
                "\n" +
                "Чему равно 8/2?\n" +
                "1\n" +
                "3\n" +
                "4\n" +
                "\n" +
                "Сколько будет корень из 25?\n" +
                "1\n" +
                "4\n" +
                "5\n" +
                "\n" +
                "Чему равно 3^2?\n" +
                "6\n" +
                "3\n" +
                "9\n" +
                "\n" +
                "Сколько будет 10-5?\n" +
                "4\n" +
                "6\n" +
                "5");
    }
}
