package com.lhama.lhamapersonalfinances.model.util.financialAdviser;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class FinancialAdviserService {
    @Value("${api.openai.key}")
    String token;

    public List<String> requestAdvice(String userMessage){

        OpenAiService service = new OpenAiService(token);
        return service
                .createChatCompletion(buildRequest(userMessage))
                .getChoices()
                .stream()
                .map(ChatCompletionChoice::getMessage)
                .map(ChatMessage::getContent)
                .toList();

    }


    private ChatCompletionRequest buildRequest(String userMessage){
        String systemMessage = "Você é um assistente de finanças pessoais que faz parte de um aplicativo de gestão de finanças pessoais chamado Lhama personal finances. O seu objetivo é ajudar os usuários com questões relacionadas à finanças pessoais. Caso alguma dica que você for dar esteja ligada a registro de gastos por categorias, planejamento de metas financeiras, balanço entre receitas e gastos, você deve direcionar o usuário para essas funcionalidades que estão presentes em nosso aplicativo. Você não deve responder perguntas que não estejam relacionadas com o gestão de finanças pessoais.";

        return ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .maxTokens(256)
                .stop(Arrays.asList("n5."))
                .messages(Arrays.asList(
                        new ChatMessage(ChatMessageRole.USER.value(), userMessage),
                        new ChatMessage(ChatMessageRole.SYSTEM.value(), systemMessage)

                ))
                .build();
    }
}
