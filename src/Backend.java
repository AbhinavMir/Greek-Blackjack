public class Backend {

    int numberOfPlayers;
    int numberOfRounds;
    Player[] players;
    int[][] scoreBoard; // dict
    int[] draws;

    enum GameName {
        TICTACTOE, ORDERANDCHAOS, TRIANTAENA
    }

    enum CellState {
        EMPTY, X, O
    }

    interface ruleSet {
        boolean isValidMove(Cell move, int boardSize);
        boolean ifPlayerWon(Cell move, Board board, int boardSize);
    }

    static class Cell {
        int x_coord;
        int y_coord;
        public CellState state;
        Player player;

        public Cell(int x, int y, CellState state) {
            this.x_coord = x;
            this.y_coord = y;
            this.state = state;

        }
    }

    class Board {
        public int boardSize;
        public Cell[][] grid = new Cell[boardSize][boardSize];

        public Board(int boardSize) {
            this.boardSize = boardSize;
            grid = new Cell[boardSize][boardSize];
            for (int i = 0; i < boardSize; i++) {
                for (int j = 0; j < boardSize; j++) {
                    grid[i][j] = new Cell(i, j, CellState.EMPTY);
                }
            }
        }
    }

    static class Player {
        int id;
        String name;
        int score;
        boolean isHuman;
        boolean isTurn;
        int[] draw;
        CellState assignedMove;
        boolean isWinner = false;

        public Player(int id, String name) {
            this.id = id;
            this.name = name;
            this.isHuman = false;
            this.score = 0;
            this.isTurn = false;
        }
    }

    class Team {
        int numPlayers;
        Player[] players;
    }

    public Backend(int numberOfPlayers, int numberOfRounds) {
        this.numberOfPlayers = numberOfPlayers;
        this.numberOfRounds = numberOfRounds;
        this.players = new Player[numberOfPlayers];
        this.scoreBoard = new int[numberOfPlayers][numberOfRounds];
        this.draws = new int[numberOfRounds];
    }

}