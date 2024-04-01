package org.minbros.chatbot.dto.pinecone;

import lombok.*;

import java.util.List;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class FetchRequest {
    @NonNull
    private List<String> ids;

    @Builder.Default
    private String namespace = "uos";

    // ids List가 "ids=id-1&ids=id-2&ids=id-3"와 같이 문자열로 변환됨
    public String toUri() {
        StringBuilder uri = new StringBuilder();
        uri.append("/vectors/fetch?");

        for (String id : this.ids) {
            uri.append(String.format("ids=%s", id));
            uri.append("&");
        }
        uri.append(String.format("namespace=%s", this.namespace));

        return uri.toString();
    }
}
