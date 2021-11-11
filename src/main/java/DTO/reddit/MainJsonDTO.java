package DTO.reddit;

public class MainJsonDTO {

    private InfoJsonDTO[] children;

    public MainJsonDTO(InfoJsonDTO[] children) {
        this.children = children;
    }

    public InfoJsonDTO[] getChildren() {
        return children;
    }
}
