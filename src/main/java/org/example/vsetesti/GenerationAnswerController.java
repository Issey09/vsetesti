package org.example.vsetesti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenerationAnswerController {
    @Autowired
    private OpenAiService openAiService;

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/answer")
    public String answer(@RequestParam String[] anwers) {
            return  openAiService.sendRequest("Вот мои ответы на твой отзыв :" + anwers  +
                    "Проанализируй ответы и скажи что мне надо подтянуть и где мои пробелы в знаниях");
    }
}
