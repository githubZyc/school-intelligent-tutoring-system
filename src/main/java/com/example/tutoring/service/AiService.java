package com.example.tutoring.service;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.utils.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AiService {

    @Value("${ai.dashscope.api-key}")
    private String apiKey;

    @Value("${ai.dashscope.model}")
    private String model;

    public String askQuestion(String question) throws ApiException, NoApiKeyException {
        Constants.apiKey = apiKey;
        
        Generation gen = new Generation();
        GenerationParam param = GenerationParam.builder()
                .model(model)
                .prompt(question)
                .build();
        
        GenerationResult result = gen.call(param);
        return result.getOutput().getText();
    }

    public String askQuestionWithMethod(String question, String method) throws ApiException, NoApiKeyException {
        String prompt = "";
        switch (method.toLowerCase()) {
            case "explain":
                prompt = "请详细解释以下知识点，使用简单易懂的语言：\n" + question;
                break;
            case "example":
                prompt = "请提供与以下问题相关的具体示例：\n" + question;
                break;
            case "exercise":
                prompt = "请生成与以下知识点相关的练习题，并提供详细的解题过程和答案：\n" + question;
                break;
            default:
                prompt = question;
        }
        
        return askQuestion(prompt);
    }
}