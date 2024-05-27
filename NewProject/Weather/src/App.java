public class App {
    public static void main(String[] args) throws Exception {
        Library test = new Library("fake API key");

        // Example
        String testfunc = test.GetCloudStateByDate(null, 0, 0, 0);
        String testfunc2 = test.GetCloudStateByHour(testfunc, 0);
    }
}
