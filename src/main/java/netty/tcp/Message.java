package netty.tcp;
//自定义协议
public class Message {
private int len; //字节长度
    private byte[] content;//内容

    public int getLen() {
        return len;
    }

    public byte[] getContent() {
        return content;
    }

    public Message(int len, byte[] content) {
        this.len = len;
        this.content = content;
    }
}
