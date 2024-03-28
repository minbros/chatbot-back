package org.minbros.chatbot.dto.pinecone.request;

import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class FetchRequest {
    @NonNull
    private List<String> ids;

    @Builder.Default
    private String namespace = "";

    // ids List가 "ids=id-1&ids=id-2&ids=id-3"와 같이 문자열로 변환됨
    public String toUri() {
        StringBuilder uri = new StringBuilder();

        uri.append("/vectors/fetch?");

        for (String id : this.ids) {
            uri.append(String.format("ids=%s", id));
            uri.append("&");
        }

        uri.append(this.namespace);
        uri.deleteCharAt(uri.length() - 1);

        return uri.toString();
    }
}
