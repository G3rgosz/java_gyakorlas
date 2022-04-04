public class Skandinav {
    public static void main(String[] args) throws Exception {
        ConnectDatabase connDb = new ConnectDatabase();
        SkandinavController skCtr = new SkandinavController(connDb); 
    }
}
