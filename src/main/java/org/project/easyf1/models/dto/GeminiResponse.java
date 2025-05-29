package org.project.easyf1.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty; // Certifique-se de ter este import
import java.util.List;

public class GeminiResponse {

    @JsonProperty("candidates")
    private List<Candidate> candidates;

    @JsonProperty("usageMetadata")
    private UsageMetadata usageMetadata;

    @JsonProperty("modelVersion")
    private String modelVersion;

    @JsonProperty("responseId")
    private String responseId;

    // Construtor padrão (necessário para o Jackson)
    public GeminiResponse() {}

    // Construtor com todos os campos (opcional, mas bom ter)
    public GeminiResponse(List<Candidate> candidates, UsageMetadata usageMetadata, String modelVersion, String responseId) {
        this.candidates = candidates;
        this.usageMetadata = usageMetadata;
        this.modelVersion = modelVersion;
        this.responseId = responseId;
    }

    // Getters e setters
    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }

    public UsageMetadata getUsageMetadata() {
        return usageMetadata;
    }

    public void setUsageMetadata(UsageMetadata usageMetadata) {
        this.usageMetadata = usageMetadata;
    }

    public String getModelVersion() {
        return modelVersion;
    }

    public void setModelVersion(String modelVersion) {
        this.modelVersion = modelVersion;
    }

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    // ---
    // Classe aninhada Candidate
    // ---
    public static class Candidate {
        @JsonProperty("content")
        private Content content;

        @JsonProperty("finishReason")
        private String finishReason;

        @JsonProperty("avgLogprobs")
        private Double avgLogprobs;

        // Construtor padrão
        public Candidate() {}

        public Candidate(Content content, String finishReason, Double avgLogprobs) {
            this.content = content;
            this.finishReason = finishReason;
            this.avgLogprobs = avgLogprobs;
        }

        // Getters e setters
        public Content getContent() {
            return content;
        }

        public void setContent(Content content) {
            this.content = content;
        }

        public String getFinishReason() {
            return finishReason;
        }

        public void setFinishReason(String finishReason) {
            this.finishReason = finishReason;
        }

        public Double getAvgLogprobs() {
            return avgLogprobs;
        }

        public void setAvgLogprobs(Double avgLogprobs) {
            this.avgLogprobs = avgLogprobs;
        }
    }

    // ---
    // Classe aninhada Content
    // ---
    public static class Content {
        @JsonProperty("parts")
        private List<Part> parts;

        @JsonProperty("role")
        private String role; // Adicionado, pois o JSON da resposta possui "role"

        // Construtor padrão
        public Content() {}

        public Content(List<Part> parts, String role) {
            this.parts = parts;
            this.role = role;
        }

        // Getters e setters
        public List<Part> getParts() {
            return parts;
        }

        public void setParts(List<Part> parts) {
            this.parts = parts;
        }

        public String getRole() {
            return role;
        }

        public void voidsetRole(String role) {
            this.role = role;
        }
    }

    // ---
    // Classe aninhada Part
    // ---
    public static class Part {
        @JsonProperty("text")
        private String text;

        // Construtor padrão
        public Part() {}

        public Part(String text) {
            this.text = text;
        }

        // Getters e setters
        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    // ---
    // Classe aninhada UsageMetadata
    // ---
    public static class UsageMetadata {
        @JsonProperty("promptTokenCount")
        private Integer promptTokenCount;

        @JsonProperty("candidatesTokenCount")
        private Integer candidatesTokenCount;

        @JsonProperty("totalTokenCount")
        private Integer totalTokenCount;

        @JsonProperty("promptTokensDetails")
        private List<TokenDetails> promptTokensDetails;

        @JsonProperty("candidatesTokensDetails")
        private List<TokenDetails> candidatesTokensDetails;

        // Construtor padrão
        public UsageMetadata() {}

        public UsageMetadata(Integer promptTokenCount, Integer candidatesTokenCount, Integer totalTokenCount, List<TokenDetails> promptTokensDetails, List<TokenDetails> candidatesTokensDetails) {
            this.promptTokenCount = promptTokenCount;
            this.candidatesTokenCount = candidatesTokenCount;
            this.totalTokenCount = totalTokenCount;
            this.promptTokensDetails = promptTokensDetails;
            this.candidatesTokensDetails = candidatesTokensDetails;
        }

        // Getters e setters
        public Integer getPromptTokenCount() {
            return promptTokenCount;
        }

        public void setPromptTokenCount(Integer promptTokenCount) {
            this.promptTokenCount = promptTokenCount;
        }

        public Integer getCandidatesTokenCount() {
            return candidatesTokenCount;
        }

        public void setCandidatesTokenCount(Integer candidatesTokenCount) {
            this.candidatesTokenCount = candidatesTokenCount;
        }

        public Integer getTotalTokenCount() {
            return totalTokenCount;
        }

        public void setTotalTokenCount(Integer totalTokenCount) {
            this.totalTokenCount = totalTokenCount;
        }

        public List<TokenDetails> getPromptTokensDetails() {
            return promptTokensDetails;
        }

        public void setPromptTokensDetails(List<TokenDetails> promptTokensDetails) {
            this.promptTokensDetails = promptTokensDetails;
        }

        public List<TokenDetails> getCandidatesTokensDetails() {
            return candidatesTokensDetails;
        }

        public void setCandidatesTokensDetails(List<TokenDetails> candidatesTokensDetails) {
            this.candidatesTokensDetails = candidatesTokensDetails;
        }
    }

    // ---
    // Classe aninhada TokenDetails
    // ---
    public static class TokenDetails {
        @JsonProperty("modality")
        private String modality;

        @JsonProperty("tokenCount")
        private Integer tokenCount;

        // Construtor padrão
        public TokenDetails() {}

        public TokenDetails(String modality, Integer tokenCount) {
            this.modality = modality;
            this.tokenCount = tokenCount;
        }

        // Getters e setters
        public String getModality() {
            return modality;
        }

        public void setModality(String modality) {
            this.modality = modality;
        }

        public Integer getTokenCount() {
            return tokenCount;
        }

        public void setTokenCount(Integer tokenCount) {
            this.tokenCount = tokenCount;
        }
    }
}