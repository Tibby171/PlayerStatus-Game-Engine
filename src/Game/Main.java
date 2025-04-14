import java.util.Scanner;

class PlayerStatus {
    private static String gameName;
    private String nickname ;
    private String weaponInHand;
    private int score, health;
    private int lives;
    private double positionX, positionY;
    final int knifeScore = 1000;
    final int sniperScore = 10000;
    final int kalashnikovScore = 20000;

    public String getNickname(){
        return nickname;
    }

    public void initPlayer(String nickname) {
        this.nickname = nickname;
        this.health = 100;
    }

    public void initPlayer(String nickname, int lives) {
        this.nickname = nickname;
        this.lives = lives;
        this.health = 100;
    }
    public void initPlayer(String nickname, int lives, int score) {
        this.nickname = nickname;
        this.lives = lives;
        this.score = score;
        this.health = 100;
    }

    public static void setGameName(String gameName){
        PlayerStatus.gameName = gameName;
    }

    public static String getGameName() {
        return gameName;
    }

    public boolean numarPerfect(int n){
        int sum = 0;
        for (int i = 1; i <= n / 2; i++){
            if (n % i == 0){
                sum += i;
            }
        }
        if (sum != n){
            return false;
        }
        return true;
    }

    public static boolean nrPrim(int numar) {
        if (numar <= 1) {
            return false; // Numerele mai mici sau egale cu 1 nu sunt prime
        }
        for (int i = 2; i <= numar / 2; i++) { // Verificam divizorii până la jumatatea numarului
            if (numar % i == 0) {
                return false; // Daca gasim un divizor inseamna ca numarul nu este prim
            }
        }
        return true;
    }

    public int sumaCifrelor(int numar){
        int sum = 0;
        while (numar > 0){
            sum = numar % 10;
            numar /= 10;
        }
        return sum;
    }

    public void findArtifact (int artifactCode){
        if (numarPerfect(artifactCode))
        {
            this.score += 5000;
            this.lives++;
            this.health = 100;
        } else if (nrPrim(artifactCode)){
            this.score += 1000;
            this.lives += 2;
            this.health = health + 25;
            if (this.health > 100)
                this.health = 100;
        }else if (artifactCode % 2 == 0 && sumaCifrelor(artifactCode) % 3 == 0){
            this.score -= 3000;
            this.health -= 25;
            if(this.health <= 0) {
                this.lives -= 1;
                this.health = 100;
                if(this.lives < 1){
                    System.out.println("Game Over");
                }
            }
        }else this.score += artifactCode;
    }

    public int getKnifeScore() {
        return knifeScore;
    }

    public int getSniperScore() {
        return sniperScore;
    }

    public int getKalashnikovScore() {
        return kalashnikovScore;
    }

    public Boolean setWeaponInHand(String weaponInHandVal){
        if(isWeaponValid(weaponInHandVal) && seteazaWeaponInHand(weaponInHandVal)) {
            weaponInHand = weaponInHandVal;
            return true;
        }
        return false;
    }

    public String getWeaponInHand() {
        return weaponInHand;
    }

    private boolean isWeaponValid(String weapon){
        return weapon.equals("knife") || weapon.equals("sniper") || weapon.equals("kalashnikov");
    }

    public boolean seteazaWeaponInHand (String input){
        switch (input){
            case ("knife"):
                if(this.score >= getKnifeScore()){
                    this.weaponInHand = "knife";
                    this.score = this.score - getKnifeScore();
                    return true;
                }
                break;
            case ("sniper"):
                if(this.score >= getSniperScore()){
                    this.weaponInHand = "sniper";
                    this.score = this.score - getSniperScore();
                    return true;
                }
                break;
            case ("kalashnikov"):
                if(this.score >= getKalashnikovScore()){
                    this.weaponInHand = "kalashnikov";
                    this.score = this.score - getKalashnikovScore();
                    return true;
                }
                break;
            default:
                break;
        }
        return false;
    }

    public void setPositionX(double positionXVal) {
        positionX = positionXVal;
    }

    public void setPositionY(double positionYVal) {
        positionY = positionYVal;
    }

    public void movePlayerTo(double positionXValue, double positionYValue){
        setPositionX(positionXValue);
        setPositionY(positionYValue);
    }

    public double calculeazaDistanta (PlayerStatus opponent){
        return Math.sqrt(Math.pow(this.positionX - opponent.positionX, 2) +
                Math.pow(this.positionY - opponent.positionY, 2));
    }

    private boolean determinaRezultatAtac(PlayerStatus opponent, double distance){
        String myWeapon = this.weaponInHand;
        String oppWeapon = opponent.weaponInHand;
        if(myWeapon == oppWeapon){
            double myChances = (3 * health + score / 1000.0) / 4.0;
            double oppChances = (3 * opponent.health + opponent.score / 1000.0) / 4.0;
            return myChances > oppChances;
        }else {
            return compareWeapons(myWeapon, oppWeapon, distance);
        }
    }

    public boolean compareWeapons(String myWeapon, String oppWeapon, double distance){
        String strongerWeapon = "";
        if(distance > 1000){
            if(myWeapon.equals("sniper") || oppWeapon.equals("sniper")){
                strongerWeapon = "sniper";
            } else if (myWeapon.equals("kalashnikov") || oppWeapon.equals("kalashnikov")) {
                strongerWeapon = "kalashnikov";
            }else{
                strongerWeapon = "knife";
            }
        }
        if(distance <= 1000){
            if(myWeapon.equals("kalashnikov") || oppWeapon.equals("kalashnikov")) {
                strongerWeapon = "kalashnikov";
            } else if (myWeapon.equals("sniper") || oppWeapon.equals("sniper")){
                strongerWeapon = "sniper";
            }else{
                strongerWeapon = "knife";
            }
        }
        if(myWeapon.equals(strongerWeapon))
            return true;
        else return false;
    }

    public boolean shouldAttackOpponent(PlayerStatus opponent){
        double distance = calculeazaDistanta(opponent);
        return determinaRezultatAtac(opponent, distance);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int testCase = scanner.nextInt();
        PlayerStatus player1;
        PlayerStatus player2;
        PlayerStatus player3;

        switch (testCase) {
            case 0:
                // Sanity check
                System.out.println("Sanity check");
                break;
            case 1:
                // Check fields, initializers, getters, setters
                player1 = new PlayerStatus();
                player1.initPlayer("player1");
                player1.initPlayer("player1", 1, 999999);
                player2 = new PlayerStatus();
                player2.initPlayer("player2", 1);
                player2.initPlayer("player2", 1, 9999999);
                player3 = new PlayerStatus();
                player3.initPlayer("player3", 2, 9999999);

                System.out.println(player1.getNickname());
                System.out.println(player2.getNickname());
                System.out.println(player3.getNickname());

                PlayerStatus.setGameName("World of DevMind");
                System.out.println(PlayerStatus.getGameName());
                PlayerStatus.setGameName("Call of DevMind");
                System.out.println(PlayerStatus.getGameName());

                player1.setWeaponInHand("knife");
                System.out.println(player1.getWeaponInHand());
                player1.setWeaponInHand("lightsaber");
                System.out.println(player1.getWeaponInHand());
                player2.setWeaponInHand("kalashnikov");
                System.out.println(player2.getWeaponInHand());
                player3.setWeaponInHand("sniper");
                System.out.println(player3.getWeaponInHand());
                break;
            case 2:
                // Test findArtifact for traps and player death
                player1 = new PlayerStatus();
                player1.initPlayer("player1", 1, 999999);

                player1.findArtifact(300);
                player1.findArtifact(300);
                player1.findArtifact(300);
                player1.findArtifact(300);
                player1.findArtifact(300);
                break;
            case 3:
                // Test findArtifact for traps and gaining lives
                player1 = new PlayerStatus();
                player1.initPlayer("player1", 1, 999999);

                player1.findArtifact(28);
                player1.findArtifact(300);
                player1.findArtifact(300);
                player1.findArtifact(300);
                player1.findArtifact(300);
                player1.findArtifact(300);

                System.out.println("empty");
                break;
            case 4:
                // Test findArtifact for gaining score and spending it on weapons
                player1 = new PlayerStatus();
                player1.initPlayer("player1", 1, 0);

                player1.findArtifact(5000);
                System.out.println(player1.setWeaponInHand("sniper"));
                player1.findArtifact(5000);
                System.out.println(player1.setWeaponInHand("sniper"));
                System.out.println(player1.setWeaponInHand("sniper"));
                player1.findArtifact(19000);
                System.out.println(player1.setWeaponInHand("kalashnikov"));
                player1.findArtifact(1000);
                System.out.println(player1.setWeaponInHand("kalashnikov"));
                System.out.println(player1.getWeaponInHand());
                break;
            case 5:
                // Test shouldAttackOpponent for close distance different weapon duel
                player1 = new PlayerStatus();
                player1.initPlayer("player1", 1, 999999999);
                player2 = new PlayerStatus();
                player2.initPlayer("player2", 1, 999999999);
                player1.movePlayerTo(0, 0);
                player2.movePlayerTo(100, 100);
                player1.setWeaponInHand("knife");
                player2.setWeaponInHand("sniper");
                System.out.println(player1.shouldAttackOpponent(player2));
                player2.setWeaponInHand("kalashnikov");
                System.out.println(player1.shouldAttackOpponent(player2));
                player1.setWeaponInHand("sniper");
                player2.setWeaponInHand("knife");
                System.out.println(player1.shouldAttackOpponent(player2));
                player2.setWeaponInHand("kalashnikov");
                System.out.println(player1.shouldAttackOpponent(player2));
                player1.setWeaponInHand("kalashnikov");
                player2.setWeaponInHand("knife");
                System.out.println(player1.shouldAttackOpponent(player2));
                player2.setWeaponInHand("sniper");
                System.out.println(player1.shouldAttackOpponent(player2));
                break;
            case 6:
                // Test shouldAttackOpponent for close distance different weapon duel
                player1 = new PlayerStatus();
                player1.initPlayer("player1", 1, 999999999);

                player2 = new PlayerStatus();
                player2.initPlayer("player2", 1, 999999999);

                player1.movePlayerTo(0, 0);
                player2.movePlayerTo(800, 800);

                player1.setWeaponInHand("knife");
                player2.setWeaponInHand("sniper");
                System.out.println(player1.shouldAttackOpponent(player2));
                player2.setWeaponInHand("kalashnikov");
                System.out.println(player1.shouldAttackOpponent(player2));

                player1.setWeaponInHand("sniper");
                player2.setWeaponInHand("knife");
                System.out.println(player1.shouldAttackOpponent(player2));
                player2.setWeaponInHand("kalashnikov");
                System.out.println(player1.shouldAttackOpponent(player2));

                player1.setWeaponInHand("kalashnikov");
                player2.setWeaponInHand("knife");
                System.out.println(player1.shouldAttackOpponent(player2));
                player2.setWeaponInHand("sniper");
                System.out.println(player1.shouldAttackOpponent(player2));
                break;
            case 7:
                // Test shouldAttackOpponent for same weapon duel
                player1 = new PlayerStatus();
                player1.initPlayer("player1", 1, 9999);

                player2 = new PlayerStatus();
                player2.initPlayer("player2", 1, 999999999);

                player1.movePlayerTo(0, 0);
                player2.movePlayerTo(10, 10);

                player1.setWeaponInHand("knife");
                player2.setWeaponInHand("knife");

                System.out.println(player1.shouldAttackOpponent(player2));
                player2.movePlayerTo(1000, 1000);
                System.out.println(player1.shouldAttackOpponent(player2));
                break;
            case 8:
                // Test shouldAttackOpponent for same weapon duel
                player1 = new PlayerStatus();
                player1.initPlayer("player1", 1, 999999999);

                player2 = new PlayerStatus();
                player2.initPlayer("player2", 1, 99999);

                player1.movePlayerTo(0, 0);
                player2.movePlayerTo(10, 10);

                player1.setWeaponInHand("sniper");
                player2.setWeaponInHand("sniper");

                System.out.println(player1.shouldAttackOpponent(player2));
                player2.movePlayerTo(1000, 1000);
                System.out.println(player1.shouldAttackOpponent(player2));
                break;
            case 9:
                // Test shouldAttackOpponent for same weapon duel with different health
                player1 = new PlayerStatus();
                player1.initPlayer("player1", 1, 999999);

                player2 = new PlayerStatus();
                player2.initPlayer("player2", 1, 999900);

                player1.movePlayerTo(0, 0);
                player2.movePlayerTo(10, 10);

                player1.setWeaponInHand("kalashnikov");
                player2.setWeaponInHand("kalashnikov");

                System.out.println(player1.shouldAttackOpponent(player2));
                player1.findArtifact(300);
                player2.movePlayerTo(1000, 1000);
                System.out.println(player1.shouldAttackOpponent(player2));
                break;
            case 10:
                // Test shouldAttackOpponent for same weapon duel with different health
                player1 = new PlayerStatus();
                player1.initPlayer("player1", 1, 999999);

                player2 = new PlayerStatus();
                player2.initPlayer("player2", 1, 999900);

                player1.movePlayerTo(0, 0);
                player2.movePlayerTo(10, 10);

                player1.setWeaponInHand("sniper");
                player2.setWeaponInHand("sniper");

                System.out.println(player1.shouldAttackOpponent(player2));

                player1.findArtifact(300);
                System.out.println(player1.shouldAttackOpponent(player2));

                player1.findArtifact(7);
                System.out.println(player1.shouldAttackOpponent(player2));

                player1.findArtifact(2000);
                System.out.println(player1.shouldAttackOpponent(player2));
                break;
        }
    }
}