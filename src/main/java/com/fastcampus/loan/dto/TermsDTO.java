package com.fastcampus.loan.dto;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

public class TermsDTO implements Serializable {

        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @Getter
        @Setter
        public static class Request {

            private String name;

            private String termsDetailURL;

        }

        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @Getter
        @Setter
        public static class Response {

            private Long termsId;

            private String name;

            private String termsDetailURL;

            private LocalDateTime createdAt;

            private LocalDateTime updatedAt;

        }

}
