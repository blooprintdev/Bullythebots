public class WaveManager {
    private GamePanel gamePanel;
    public int currentWave = 1; // public für UI
    private int enemiesToSpawn = 5;
    private int spawnTimer = 0;
    private int waveBreak = 120;

    public WaveManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        reset();
    }

    public void reset() {
        currentWave = 1;
        enemiesToSpawn = 5;
        spawnTimer = 0;
        waveBreak = 300;
    }

    public void update() {
        if (enemiesToSpawn > 0 && spawnTimer++ % 60 == 0) {
            gamePanel.spawnEnemy();
            enemiesToSpawn--;
        } else if (enemiesToSpawn == 0 && gamePanel.getEnemies().isEmpty() && waveBreak-- <= 0) {
            currentWave++;
            if (currentWave > 100) currentWave = 100;
            enemiesToSpawn = 5 + (currentWave * 2);
            waveBreak = 120;
        }
    }

    public int getCurrentWave() { return currentWave; }
}