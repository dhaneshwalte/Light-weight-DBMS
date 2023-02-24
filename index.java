import java.io.IOException;
import java.util.*;

public class index{
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);        
        AuthService authService = new AuthService();
        System.out.println("1. Login\n2. Register");
        int choice = Integer.parseInt(scanner.nextLine());
        if (choice == 1){
            System.out.println("Enter username");
            String username = scanner.nextLine();
            System.out.println("Enter Password");
            String password = scanner.nextLine();
            if (!authService.authenticate(username, password)){
                System.out.println("Invalid Credentials");
                scanner.close();
                return;
            }
        }
        else if (choice == 2){
            System.out.println("Enter username");
            String username = scanner.nextLine();
            System.out.println("Enter Password");
            String password = scanner.nextLine();
            if(authService.register(username, password)){
                System.out.println("Registration Successful");
            }else{
                System.out.println("User already exists");
            }
        }
        scanner.close();
    }

    public void testSqlParser(){
        //SqlParser sqlParser = new SqlParser("Select username, usertext from user where age > 18 and time < 8");
        //SqlParser sqlParser = new SqlParser("INSERT INTO user VALUES (1,2)");
        //SqlParser sqlParser = new SqlParser("Update user set username = DAN, age = 18 where age = 11 and some=12");
        //SqlParser sqlParser = new SqlParser("DELETE FROM Customers WHERE CustomerName=\"Alfreds Barnes\" and cust=12");
        SqlParser sqlParser = new SqlParser("""
            CREATE TABLE users(
            user_id INT PRIMARY KEY,
            username VARCHAR(40) UNIQUE,
            password VARCHAR(255),
            email VARCHAR(255) NOT NULL
         );
        """);
        Query q = sqlParser.parse();
        System.out.println(q);
    }

    public void testDbService(){
        DbService dbService = new DbService();
        Table userTable = dbService.load("user");
        for (Map<String, Object> row: userTable.values){
            row.forEach((key, value) -> System.out.println(key + ":" + value));
        }
        dbService.createTable("user2", new ArrayList<>(Arrays.asList("user1", "user2")));
        Map<String, Object> user1 = new HashMap<>(Map.of("user1", "dan", "user2", 25));
        Map<String, Object> user2 = new HashMap<>(Map.of("user1", "dan", "user2", 26));
        dbService.insert("user2", user1);
        dbService.insert("user2", user2);
        userTable = dbService.select("user2", null);
        for (Map<String, Object> row: userTable.values){
            row.forEach((key, value) -> System.out.println(key + ":" + value));
        }
        System.out.println("deleting");
        Map<String, Object> remove = new HashMap<>(Map.of("user2", 25));
        dbService.delete("user2", remove);
        userTable = dbService.select("user2", null);
        System.out.println(userTable.values.size());
        for (Map<String, Object> row: userTable.values){
            row.forEach((key, value) -> System.out.println(key + ":" + value));
        }
    }
}