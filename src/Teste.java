
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Teste {

    public static void main(String[] args) throws IOException, InterruptedException {

        //SYSTEM
        Random r = new Random();
        Scanner scanner = new Scanner(System.in);

        //CONTROLE
        boolean game = true;
        boolean validMove = true;
        boolean generatePlayer = false;
        boolean generateRobots = true;

        //INFORMACOES GERAIS
        int qteRobos = 3;
        int linhas = 13;
        int colunas = 21;
        int[][] matriz = new int[linhas][colunas];

        //PLAYER
        Player.setX(linhas / 2);
        Player.setY(colunas / 2);
        matriz[(linhas / 2)][(colunas / 2)] = 1;

        //ROBOTS
        List<Robot> robots = new ArrayList<>();

        //RUBBLES
        List<Rubble> rubbles = new ArrayList<>();

        while (game) {
            if (generatePlayer) {
                matriz[(Player.getX())][Player.getY()] = 0;
                Player.setX(linhas / 2);
                Player.setY(colunas / 2);
                matriz[(linhas / 2)][(colunas / 2)] = 1;
                generatePlayer = false;
            }

            if (generateRobots) {
                robots.clear();
                rubbles.clear();

                for (int i = 0; i < qteRobos; i++) {
                    int random1 = r.nextInt((11 - 1) + 1) + 1;
                    int random2 = r.nextInt((11 - 1) + 1) + 1;

                    if (Player.getSafeZone().contains(random1) || Player.getSafeZone().contains(random2) || matriz[random1][random2] == 2) {
                        i--;
                    } else {
                        Robot robot = new Robot(random1, random2);
                        robots.add(robot);
                        matriz[robot.getX()][robot.getY()] = 2;
                    }
                }
                generateRobots = false;
            }

            if (!validMove) {
                System.out.println("Este não é um movimento válido!");
                validMove = true;
            }
            System.out.println("--------------------------- NIVEL " + (qteRobos - 2) + " -----------------------------");
            for (int x = 0; x < linhas; x++) {
                for (int y = 0; y < colunas; y++) {

                    if (x == 0) {
                        System.out.print(" _");

                    } else if (y == (colunas - 1)) {

                        if (matriz[x][y] == 1) {
                            System.out.print("|P|");
                        } else if (matriz[x][y] == 2) {
                            System.out.print("|R|");
                        } else if (matriz[x][y] == 3) {
                            System.out.print("|E|");
                        } else {
                            System.out.print("|_|");
                        }
                    } else if (matriz[x][y] == 1) {
                        System.out.print("|P");
                    } else if (matriz[x][y] == 2) {
                        System.out.print("|R");
                    } else if (matriz[x][y] == 3) {
                        System.out.print("|E");
                    } else {
                        System.out.print("|_");
                    }
                }
                if (x == 0) {
                    System.out.print(" _ _ _ _ _ _ _ _ _ _ _");
                }
                if (x == 1) {
                    System.out.print("       Controls      ");
                }
                if (x == 2) {
                    System.out.print("          8          ");
                }
                if (x == 3) {
                    System.out.print("       7     9       ");
                }
                if (x == 4) {
                    System.out.print("    4     ×     6    ");
                }
                if (x == 5) {
                    System.out.print("       1     3       ");
                }
                if (x == 6) {
                    System.out.print("          2          ");
                }
                if (x == 7) {
                    System.out.print("_ _ _ _ _ _ _ _ _ _ _");
                }
                if (x == 8) {
                    System.out.print("                     ");
                }
                if (x == 9) {
                    System.out.print("Teleport => -        ");
                }
                if (x == 10) {
                    System.out.print("Safe Teleport(" + Player.getQteSafeTeleport() + ") => +");
                }
                if (x == 11) {
                    System.out.print("Exit Game => 0       ");
                }
                if (x == 12) {
                    System.out.print("_ _ _ _ _ _ _ _ _ _ _");
                }
                if (x != 0) {
                    System.out.println("|");
                } else {
                    System.out.println();
                }
            }

            Character input = scanner.next().charAt(0);

            if (input == '0') {
                game = false;
            } else {
                List<Rubble> cantMove;
                int x = Player.getX();
                int y = Player.getY();

                switch (input) {
                    case '1':
                        cantMove = rubbles.stream()
                                .filter(item -> item.getX() == Player.getX() + 1 && item.getY() == Player.getY() - 1)
                                .collect(Collectors.toList());
                        if (x + 1 == linhas || y - 1 < 0 || cantMove.size() > 0) {
                            validMove = false;
                            break;
                        }
                        matriz[x][y] = 0;
                        Player.setX(++x);
                        Player.setY(--y);
                        matriz[Player.getX()][Player.getY()] = 1;
                        break;
                    case '2':
                        cantMove = rubbles.stream()
                                .filter(item -> item.getX() == Player.getX() + 1 && item.getY() == Player.getY())
                                .collect(Collectors.toList());
                        if (x + 1 == linhas) {
                            validMove = false;
                            break;
                        }
                        matriz[x][y] = 0;
                        Player.setX(++x);
                        matriz[Player.getX()][Player.getY()] = 1;
                        break;
                    case '3':
                        cantMove = rubbles.stream()
                                .filter(item -> item.getX() == Player.getX() + 1 && item.getY() == Player.getY() + 1)
                                .collect(Collectors.toList());
                        if (x + 1 == linhas || y + 1 == colunas || cantMove.size() > 0) {
                            validMove = false;
                            break;
                        }
                        matriz[x][y] = 0;
                        Player.setX(++x);
                        Player.setY(++y);
                        matriz[Player.getX()][Player.getY()] = 1;
                        break;
                    case '4':
                        cantMove = rubbles.stream()
                                .filter(item -> item.getX() == Player.getX() && item.getY() == Player.getY() - 1)
                                .collect(Collectors.toList());
                        if (y - 1 < 0 || cantMove.size() > 0) {
                            validMove = false;
                            break;
                        }
                        matriz[x][y] = 0;
                        Player.setY(--y);
                        matriz[Player.getX()][Player.getY()] = 1;
                        break;
                    case '6':
                        cantMove = rubbles.stream()
                                .filter(item -> item.getX() == Player.getX() && item.getY() == Player.getY() + 1)
                                .collect(Collectors.toList());
                        if (y + 1 == colunas || cantMove.size() > 0) {
                            validMove = false;
                            break;
                        }
                        matriz[x][y] = 0;
                        Player.setY(++y);
                        matriz[Player.getX()][Player.getY()] = 1;
                        break;
                    case '7':
                        cantMove = rubbles.stream()
                                .filter(item -> item.getX() == Player.getX() - 1 && item.getY() == Player.getY() - 1)
                                .collect(Collectors.toList());
                        if (x - 1 < 1 || y - 1 < 0 || cantMove.size() > 0) {
                            validMove = false;
                            break;
                        }
                        matriz[x][y] = 0;
                        Player.setX(--x);
                        Player.setY(--y);
                        matriz[Player.getX()][Player.getY()] = 1;
                        break;
                    case '8':
                        cantMove = rubbles.stream()
                                .filter(item -> item.getX() == Player.getX() - 1 && item.getY() == Player.getY())
                                .collect(Collectors.toList());
                        if (x - 1 < 1 || cantMove.size() > 0) {
                            validMove = false;
                            break;
                        }
                        matriz[x][y] = 0;
                        Player.setX(--x);
                        matriz[Player.getX()][Player.getY()] = 1;
                        break;
                    case '9':
                        cantMove = rubbles.stream()
                                .filter(item -> item.getX() == Player.getX() - 1 && item.getY() == Player.getY() + 1)
                                .collect(Collectors.toList());
                        if (x - 1 < 1 || y + 1 == colunas || cantMove.size() > 0) {
                            validMove = false;
                            break;
                        }
                        matriz[x][y] = 0;
                        Player.setX(--x);
                        Player.setY(++y);
                        matriz[Player.getX()][Player.getY()] = 1;
                        break;
                    case '-':
                        matriz[x][y] = 0;
                        Player.teleport(matriz);
                        matriz[Player.getX()][Player.getY()] = 1;
                        break;
                    case '+':
                        if(Player.getQteSafeTeleport() > 0){
                            matriz[x][y] = 0;
                            Player.safeTeleport(robots, rubbles);
                            matriz[Player.getX()][Player.getY()] = 1;
                            break;
                        } else {
                            validMove = false;
                            break;
                        }
                    default:
                        validMove = false;
                }

                if (validMove) {
                    robots.forEach(item -> distanceBetween(item));
                    Collections.sort(robots);

                    robots.forEach(robot -> {
                        if (!robot.isDead()) {

                            int robotX = robot.getX();
                            int robotY = robot.getY();
                            if (Player.getX() > robot.getX()) {
                                robotX++;
                            } else if (Player.getX() < robot.getX()) {
                                robotX--;
                            }

                            if (Player.getY() > robot.getY()) {
                                robotY++;
                            } else if (Player.getY() < robot.getY()) {
                                robotY--;
                            }

                            matriz[robot.getX()][robot.getY()] = 0;
                            robot.setX(robotX);
                            robot.setY(robotY);
                            List<Robot> samePositionRobots = robots.stream()
                                    .filter(item -> (item.getX() == robot.getX()
                                            && item.getY() == robot.getY()))
                                    .collect(Collectors.toList());

                            if (samePositionRobots.size() > 1) {
                                samePositionRobots.forEach(item -> item.kill());
                                rubbles.add(new Rubble(robotX, robotY));
                                matriz[robotX][robotY] = 3;
                            } else if (rubbles.size() > 0) {
                                rubbles.forEach(item -> {
                                    if (item.getX() == robot.getX() && item.getY() == robot.getY()) {
                                        robot.kill();
                                    } else {
                                        matriz[robot.getX()][robot.getY()] = 2;
                                    }
                                });
                            } else {
                                matriz[robot.getX()][robot.getY()] = 2;
                            }
                        }
                    });

                    if (robots.stream().allMatch(item -> item.isDead())) {
                        rubbles.forEach(item -> matriz[item.getX()][item.getY()] = 0);
                        Player.addSafeTeleport();
                        generatePlayer = true;
                        generateRobots = true;
                        qteRobos++;
                    } else if (robots.stream().anyMatch(item -> (!item.isDead() && item.getX() == Player.getX() && item.getY() == Player.getY()))) {
                        boolean validInput = false;
                        while (!validInput) {
                            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                            System.out.println("------------------------- GAME OVER -----------------------------");
                            System.out.println("------------------ DESEJA JOGAR NOVAMENTE(Y/N) ------------------");
                            Character playAgain = scanner.next().charAt(0);
                            if (playAgain == 'y' || playAgain == 'Y') {
                                validInput = true;
                                generateRobots = true;
                                generatePlayer = true;
                                Player.resetSafeTeleport();
                                qteRobos = 3;
                                robots.forEach(robot -> matriz[robot.getX()][robot.getY()] = 0);

                            } else if (playAgain == 'n' || playAgain == 'N') {
                                validInput = true;
                                game = false;
                            }
                        }
                    }
                }

            }
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        }

    }

    private static class Player {
        private static int x;
        private static int y;
        private static int qteSafeTeleport = 0;
        private static List<Integer> safeZone = Arrays.asList(5, 6, 7);

        public static int getX() {
            return x;
        }

        public static void setX(int x) {
            Player.x = x;
        }

        public static int getY() {
            return y;
        }

        public static void setY(int y) {
            Player.y = y;
        }

        public static int getQteSafeTeleport() {
            return qteSafeTeleport;
        }

        public static void resetSafeTeleport() {
            qteSafeTeleport = 0;
        }

        public static void addSafeTeleport() {
            Player.qteSafeTeleport++;
        }

        public static List<Integer> getSafeZone() {
            return safeZone;
        }

        public static void teleport(int[][] matriz) {
            Random r = new Random();
            boolean invalidTeleport = true;
            while (invalidTeleport) {
                int random1 = r.nextInt((11 - 1) + 1) + 1;
                int random2 = r.nextInt((11 - 1) + 1) + 1;

                if (!(matriz[random1][random2] == 2)) {
                    invalidTeleport = false;
                    Player.setX(random1);
                    Player.setY(random2);
                }
            }
        }

        public static void safeTeleport(List<Robot> robots, List<Rubble> rubbles) {
            Random r = new Random();
            boolean invalidTeleport = true;

            int robotsSize = robots.stream().filter(item -> !item.isDead()).collect(Collectors.toList()).size();
            List<Integer> dangersZoneX = robots.stream().filter(item -> !item.isDead()).map(item -> item.getX()).collect(Collectors.toList());
            List<Integer> dangersZoneY = robots.stream().filter(item -> !item.isDead()).map(item -> item.getY()).collect(Collectors.toList());

            for (int i = 0; i < robotsSize; i++) {
                Robot robot = robots.get(i);

                if (!dangersZoneX.contains(robot.getX() + 1)) {
                    dangersZoneX.add(robot.getX() + 1);
                }

                if (!dangersZoneX.contains(robot.getX() - 1)) {
                    dangersZoneX.add(robot.getX() - 1);
                }

            }

            for (int i = 0; i < robotsSize; i++) {
                Robot robot = robots.get(i);

                if (!dangersZoneY.contains(robot.getY() + 1)) {
                    dangersZoneY.add(robot.getY() + 1);
                }

                if (!dangersZoneY.contains(robot.getY() - 1)) {
                    dangersZoneY.add(robot.getY() - 1);
                }

            }

            while (invalidTeleport) {
                int random1 = r.nextInt((11 - 1) + 1) + 1;
                int random2 = r.nextInt((11 - 1) + 1) + 1;

                if (!(dangersZoneX.contains(random1) && dangersZoneY.contains(random2))) {
                    if (!(rubbles.stream().anyMatch(item -> item.getX() == random1 && item.getY() == random2))) {
                        invalidTeleport = false;
                        Player.setX(random1);
                        Player.setY(random2);
                        Player.qteSafeTeleport--;
                    }
                }
            }
        }
    }

    private static void distanceBetween(Robot robot) {

        robot.setDistanceBetween(Math.sqrt(Math.pow(robot.getX() - Player.getY(), 2) +
                Math.pow(robot.getY() - Player.getY(), 2)));
    }
}