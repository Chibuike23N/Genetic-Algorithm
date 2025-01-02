/**
 * @Name: Chibuike Nnolim
 * @ID: 7644941
 * @01-01-2025
 *
 * @Class Menu - This class mimics a chromosome within a generation for the genetic algorithm.
 */
import java.util.Random;

public class Chromosome
{
    public int tuples[][];
    public double fitness = 0.0;
    public Chromosome(int size)
    {
        tuples = new int[size][3];
        generateData();
    }

    /**
     * Method initializes the data within a chromosome once it is created.
     */
    private void generateData()
    {
        for(int i = 0; i < tuples.length; i++)
        {
            Random random = new Random();
            tuples[i][0] = random.nextInt(0, Menu.courseDataSize);
            tuples[i][1] = random.nextInt(0, Menu.roomDataSize);
            tuples[i][2] = random.nextInt(0, Menu.timeDataSize);
        }
    }

    /**
     * Method sets the fitness of the current chromosome
     * @param fitness the fitness to be set
     */
    public void setFitness(double fitness)
    {
        this.fitness = fitness;
    }
}
