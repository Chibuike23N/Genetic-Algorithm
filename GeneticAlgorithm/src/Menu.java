/**
 * @Name: Chibuike Nnolim
 * @ID: 7644941
 * @01-01-2025
 *
 * @Class Menu - This class displays a menu for the user to run the genetic algorithm for the timetable scheduling
 * problem
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class Menu
{
    public List<String[]> courses = new ArrayList<>();
    public List<String[]> rooms = new ArrayList<>();
    public List<String[]> timeSlots = new ArrayList<>();

    public int chromosomeLength;
    private int crossChoice = 1;

    private int generationIndexOfSolution;

    public int popSize = 500;
    public int maxGens = 100;
    public int crossRate = 100;
    public int mutateRate = 0;
    public static int courseDataSize = 0;
    public static int roomDataSize = 0;
    public static int timeDataSize = 0;
    public Menu()
    {
        try
        {
            //Initialize timetable data
            File courseFile = new File("courses.txt");
            Scanner c = new Scanner(courseFile);
            c.nextLine();
            addElements(c,courses);
            courseDataSize = courses.size();
            chromosomeLength = courseDataSize;

            File roomFile = new File("rooms.txt");
            Scanner r   = new Scanner(roomFile);
            r.nextLine();
            addElements(r,rooms);
            roomDataSize = rooms.size();

            File timeFile = new File("timeslots.txt");
            Scanner t = new Scanner(timeFile);
            t.nextLine();
            addElements(t,timeSlots);
            timeDataSize = timeSlots.size();




            //Menu - Loop until user decides to end program
            for(;;)
            {
                //Display Parameters & Menu
                System.out.println("Population Size: " + popSize);
                System.out.println("Maximum Generations: " + maxGens);
                System.out.println("Crossover Rate: " + crossRate);
                System.out.println("Mutation Rate: " + mutateRate);
                System.out.println();
                System.out.println("0. Exit Program");
                System.out.println("1. Run genetic algorithm");
                System.out.println("2. Edit Parameters");
                System.out.println();


                //Menu Options
                try
                {
                    Scanner scan = new Scanner(System.in);
                    int input = scan.nextInt();
                    switch (input)
                    {
                        case 0:
                        {
                            System.exit(0);
                        }
                        case 1:
                        {
                            Thread.sleep(1000);
                            System.out.println();
                            System.out.println("Algorithm started.");

                            final long startTime = System.nanoTime();
                            //Find solution
                            Chromosome[][] generationTable = geneticAlgorithm(maxGens, popSize, crossRate, mutateRate);
                            final long duration = System.nanoTime() - startTime;


                            Chromosome solution = generationTable[generationIndexOfSolution][0];
                            System.out.println("Earliest generation containing solution: " + generationIndexOfSolution);

                            //Print solution
                            System.out.println();
                            System.out.println("Solution Chromosome:");
                            for (int tuple[]: solution.tuples)
                            {
                                System.out.print("[ ");
                                for (int i = 0; i < tuple.length; i++)
                                {
                                    System.out.print(tuple[i] + " ");
                                }
                                System.out.println("]");
                            }
                            System.out.println();
                            System.out.println("Time elapsed (Nanoseconds): " + duration + "ns");
                            System.out.println();
                            break;
                        }
                        case 2:
                        {
                            System.out.println("For Crossover and Mutation rates, ensure your entry is an integer between 0 & 100.");

                            //Population size
                            for(;;)
                            {
                                try
                                {
                                    System.out.print("Population Size: ");
                                    int test = scan.nextInt();
                                    if(test < 1)//size cannot be less than 1
                                    {
                                        System.out.println();
                                        System.out.println("Invalid entry. Try again.");
                                        System.out.println();
                                    }
                                    else
                                    {
                                        popSize = test;
                                        break;
                                    }
                                }
                                catch (InputMismatchException n)
                                {
                                    System.out.println();
                                    System.out.println("Invalid entry. Try again.");
                                    System.out.println();
                                }
                            }

                            //Maximum Generations
                            for(;;)
                            {
                                try
                                {
                                    System.out.print("Maximum amount of generations: ");
                                    int test = scan.nextInt();
                                    if(test < 0)//generation amount cannot be negative
                                    {
                                        System.out.println();
                                        System.out.println("Invalid entry. Try again.");
                                        System.out.println();
                                    }
                                    else
                                    {
                                        maxGens = test;
                                        break;
                                    }
                                }
                                catch (InputMismatchException n)
                                {
                                    System.out.println();
                                    System.out.println("Invalid entry. Try again.");
                                    System.out.println();
                                }
                            }

                            //Crossover Rate
                            for(;;)
                            {
                                try
                                {
                                    System.out.print("Crossover Rate: ");
                                    int test = scan.nextInt();
                                    if(!(test >= 0 && test <= 100))//generation amount cannot be negative
                                    {
                                        System.out.println();
                                        System.out.println("Invalid entry. Try again.");
                                        System.out.println();
                                    }
                                    else
                                    {
                                        crossRate = test;
                                        break;
                                    }
                                }
                                catch (InputMismatchException n)
                                {
                                    System.out.println();
                                    System.out.println("Invalid entry. Try again.");
                                    System.out.println();
                                }
                            }

                            //Mutation Rate
                            for(;;)
                            {
                                try
                                {
                                    System.out.print("Mutation Rate: ");
                                    int test = scan.nextInt();
                                    if(!(test >= 0 && test <= 100))//generation amount cannot be negative
                                    {
                                        System.out.println();
                                        System.out.println("Invalid entry. Try again.");
                                        System.out.println();
                                    }
                                    else
                                    {
                                        mutateRate = test;
                                        break;
                                    }
                                }
                                catch (InputMismatchException n)
                                {
                                    System.out.println();
                                    System.out.println("Invalid entry. Try again.");
                                    System.out.println();
                                }
                            }
                            System.out.println();
                            System.out.println("Parameters updated.");
                            System.out.println(2);
                            break;
                        }
                        default:
                        {
                            System.out.println();
                            System.out.println("Invalid entry. Try again.");
                            System.out.println();
                            break;
                        }
                    }
                }
                catch (InputMismatchException e)
                {
                    System.out.println();
                    System.out.println("Invalid entry. Try again.");
                    System.out.println();
                }
                catch (InterruptedException e) {throw new RuntimeException(e);}

            }


        }
        catch (FileNotFoundException f)
        {
            System.out.println("");
            System.out.println("File not found");
            System.exit(0);
        }
    }

    /**
     * Method adds data to the corresponding data list.
     * @param s Scanner that reads timetable data
     * @param l Corresponding data list
     */
    public void addElements(Scanner s, List<String[]> l)
    {
        while(s.hasNext())
        {
            String read = s.nextLine();
            l.add(read.split(","));
        }
    }


    /**
     * Method begins the genetic algorithm.
     * @param maxGens the maximum amount of generations the algorithm runs for
     * @param popSize the size of each generation
     * @param crossRate the rate at which crossover will be applied to each chromosome within a generation
     * @param mutateRate the rate at which mutation will be applied to each chromosome within a generation
     * @return the table of generations (2D array of Chromosomes) along with the generation containting the solution to
     * the problem
     */
    private Chromosome[][] geneticAlgorithm(int maxGens, int popSize, int crossRate, int mutateRate)
    {
        Random box = new Random();
        
        //Initialize generation[0]
        int i = 0;
        Chromosome generations[][] = new Chromosome[maxGens][popSize];//table of all generations
        double averageFitness[] = new double[maxGens];//average fitness for each generation

        for(int j = 0; j < popSize; j++)
        {
            generations[i][j] = new Chromosome(chromosomeLength);
            generations[i][j].setFitness(getFitness(generations[i][j]));//set fitness for each chromosome
        }

        //sort initial generation[0]
        for(int j = 0; j < popSize - 1; j++)
        {
            for(int k = 0; k < popSize - j - 1; k++)
            {
                if(getFitness(generations[i][k]) < getFitness(generations[i][k + 1]))
                {
                    Chromosome temp = generations[i][k];
                    generations[i][k] = generations[i][k + 1];
                    generations[i][k + 1] = temp;
                }
            }
        }

        //Get average of current generation
        double avg = 0.0;
        for (Chromosome chrom : generations[i])
        {
            avg += getFitness(chrom);
        }
        avg /= popSize;

        // Log best fitness and diversity
        double bestFitness = getFitness(generations[i][0]); // Assumes sorted by fitness
        int diversity = calculateDiversity(generations[i]);
        System.out.println("Generation " + i + ": Average Fitness = " + avg +
                ", Best Fitness = " + bestFitness +
                ", Diversity = " + diversity);

        while(true)//loop until termination criterion
        {
            i++;//Increment i

            //Elitism - %10
            int elitismAmount = (int) (popSize * 0.1);
            if(elitismAmount < 1) elitismAmount = 1;

            for(int j = 0; j < elitismAmount; j++)
            {
                generations[i][j] = generations[i - 1][j];//bring top %10 of last generation
            }

            //Tournament Selection, Crossover, and then Mutation for rest of new population
            for(int j = elitismAmount; j < popSize; j += 2)
            {

                //Take 2 best chromosomes
                Chromosome parent1 = getParent(4, generations[i - 1]);
                Chromosome parent2 = getParent(4, generations[i - 1]);
                Chromosome child1 = new Chromosome(chromosomeLength);
                Chromosome child2 = new Chromosome(chromosomeLength);

                child1.tuples = parent1.tuples;
                child2.tuples = parent2.tuples;

                //Crossovers
                Chromosome children[];
                int crossOverChance = box.nextInt(0,101);
                if(crossOverChance < crossRate)//crossover rate
                {
                    //Perform Uniform crossover if user chooses it
                    if(crossChoice == 1)
                    {
                        children = uniformCross(parent1, parent2);
                        child1 = children[0];
                        child2 = children[1];
                    }
                    else//Perform 1-point crossover if user chooses it
                    {
                        children = onePointCross(parent1, parent2);
                        child1 = children[0];
                        child2 = children[1];
                    }
                }

                //Mutations
                int mutateChance = box.nextInt(0,101);
                if(mutateChance < mutateRate)
                {
                    //Perform Mutations on both children
                    child1 = mutate(child1);
                    child2 = mutate(child2);
                }

                //update fitness for each child
                child1.setFitness(getFitness(child1));
                child2.setFitness(getFitness(child2));

                int remainingPop = popSize - elitismAmount;
                if(remainingPop % 2 == 1)//if remaining population size is odd
                {
                    if(j == popSize - 1)//if only one position left for generation, choose better child
                    {
                        if(getFitness(child1) > getFitness(child2)) generations[i][j] = child1;
                        else if(getFitness(child1) < getFitness(child2)) generations[i][j] = child2;
                        else generations[i][j] = child1;
                    }
                    else//otherwise put both offspring to new generation
                    {
                        generations[i][j] = child1;
                        generations[i][j + 1] = child2;
                    }
                }
                else//otherwise put both offspring to new generation
                {
                    generations[i][j] = child1;
                    generations[i][j + 1] = child2;
                }
            }

            //sort new population;
            for(int j = 0; j < popSize - 1; j++)
            {
                for(int k = 0; k < popSize - j - 1; k++)
                {
                    if(getFitness(generations[i][k]) < getFitness(generations[i][k + 1]))
                    {
                        Chromosome temp = generations[i][k];
                        generations[i][k] = generations[i][k + 1];
                        generations[i][k + 1] = temp;
                    }
                }
            }

            avg = 0.0;
            for (Chromosome chrom : generations[i])
            {
                avg += getFitness(chrom);
            }
            avg /= popSize;
            averageFitness[i] = avg;

            // Log best fitness and diversity
            bestFitness = getFitness(generations[i][0]);
            diversity = calculateDiversity(generations[i]);
            double averageChange = averageFitness[i] - averageFitness[i - 1];//difference of current and last generation

            if(averageChange > 0)//average increased
            {
                System.out.println("Generation " + i + ": Average Fitness = " + avg +
                        "  {+" + averageChange + "},  Best Fitness = " + bestFitness +
                        ", Diversity = " + diversity);
            }
            else//average decreased
            {
                System.out.println("Generation " + i + ": Average Fitness = " + avg +
                        "  {" + averageChange + "},  Best Fitness = " + bestFitness +
                        ", Diversity = " + diversity);
            }

            if(i == maxGens - 1 || getFitness(generations[i][0]) == 1.0)//Termination criteria: if i has reached maximum generations OR solution is found
            {
                setIndexOfSolution(i);
                return generations;
            }
        }
    }

    /**
     * Method calculates the amount of chromosomes that are unique within a given generation/population.
     * @param population the population to be evaluated
     * @return the diversity of the population
     */
    public int calculateDiversity(Chromosome[] population) {
        Set<String> uniqueChromosomes = new HashSet<>();
        for (Chromosome c : population)
        {
            uniqueChromosomes.add(Arrays.deepToString(c.tuples)); // Convert tuples to string for comparison
        }
        return uniqueChromosomes.size();
    }


    /**
     * Method updates the global variable containing the generation index of the solution chromosome once the algorithm
     * is complete.
     * @param index the index to be set
     */
    private void setIndexOfSolution(int index)
    {
        generationIndexOfSolution = index;
    }

    /**
     * Method evaluates and returns the fitness of the passed chromosome
     * @param c the chromosome to be evaluated
     * @return the fitness of the chromosome
     */
    private double getFitness(Chromosome c)
    {
        int conflicts = 0;
        HashMap roomUsage = new HashMap();
        HashMap profSchedule = new HashMap();

        //for each tuple in chromosome
        for(int tuple[]: c.tuples)
        {
            int numOfStudents = Integer.valueOf(courses.get(tuple[0])[2]);//get number of students for current course
            int roomCapacity = Integer.valueOf(rooms.get(tuple[1])[1]);//get maximum room capacity

            if(numOfStudents > roomCapacity) conflicts += 2;

            int courseHours = Integer.valueOf(courses.get(tuple[0])[3]);
            for(int i = 0; i <= courseHours - 1; i++)
            {
                //roomUsage conflicts
                int timeSlotIndex = tuple[2];
                int currentSlot = timeSlotIndex + i;

                List<Integer> key = new ArrayList<>();
                key.add(tuple[1]);
                key.add(currentSlot);

                if(!roomUsage.containsKey(key)) roomUsage.put(key,0);

                int newNum  = (int)roomUsage.get(key) + 1;
                roomUsage.replace(key,newNum);

                if((int)(roomUsage.get(key)) > 1)conflicts += 3;

                //professor schedule conflicts
                List key2 = new ArrayList<>();
                key2.add(courses.get(tuple[0])[1]);
                key2.add(currentSlot);

                if(!profSchedule.containsKey(key2))profSchedule.put(key2,0);
                int newNum2  = (int)profSchedule.get(key2) + 1;
                profSchedule.replace(key2,newNum2);

                if((int)(profSchedule.get(key2)) > 1)conflicts++;
            }
        }
        return (double) 1 /(1 + conflicts);
    }

    /**
     * Method uses tournament selection to retrieve a parent
     * @param k tournament size
     * @param generation current generation
     * @return one parent
     */
    public Chromosome getParent(int k, Chromosome[] generation )
    {
        Random random = new Random();
        Chromosome tournament[] = new Chromosome[k];
        for(int i = 0; i < k; i++)
        {
            tournament[i] = generation[random.nextInt(0,generation.length)];
        }

        //sort Tournament
        for(int i = 0; i < tournament.length - 1; i++)
        {
            for(int j = 0; j < tournament.length - i - 1; j++)
            {
                if(getFitness(tournament[j]) < getFitness(tournament[j + 1]))
                {
                    Chromosome temp = tournament[j];
                    tournament[j] = tournament[j + 1];
                    tournament[j + 1] = temp;
                }
            }
        }

        return tournament[0];
    }

    /**
     * Method recieves two parent chromosomes and performs uniform crossover on them.
     * @param p1 parent #1
     * @param p2 parent #2
     * @return the resulting offspring children (2)
     */
    public Chromosome[] uniformCross(Chromosome p1, Chromosome p2)
    {
        Chromosome c1 = new Chromosome(chromosomeLength);
        Chromosome c2 = new Chromosome(chromosomeLength);

        for(int l = 0; l < chromosomeLength; l++)
        {
            int mask[] = generateMask();

            if(mask[l] == 0)//if 0, cross genes
            {
                c2.tuples[l] = p1.tuples[l];
                c1.tuples[l] = p2.tuples[l];
            }
            else//otherwise don't
            {
                c1.tuples[l] = p1.tuples[l];
                c2.tuples[l] = p2.tuples[l];
            }
        }

        return  new Chromosome[]{c1,c2};
    }

    /**
     * Method recieves two parent chromosomes and performs one-point crossover on them.
     * @param p1 parent #1
     * @param p2 parent #2
     * @return the resulting offspring children (2)
     */
    public Chromosome[] onePointCross(Chromosome p1, Chromosome p2)
    {
        Random box = new Random();
        Chromosome c1 = new Chromosome(chromosomeLength);
        Chromosome c2 = new Chromosome(chromosomeLength);


        int randomIndex = box.nextInt(0, chromosomeLength);

        for(int i = 0; i < chromosomeLength; i++)
        {
            if(i < randomIndex)
            {
                c1.tuples[i] = p1.tuples[i];
                c2.tuples[i] = p2.tuples[i];
            }
            else
            {
                c1.tuples[i] = p2.tuples[i];
                c2.tuples[i] = p1.tuples[i];
            }
        }

        return  new Chromosome[]{c1,c2};
    }

    /**
     * Method mutates a single chromosome by randomizing the data within one of its tuples
     * @param c chromosome to be mutated
     * @return mutated chromosome
     */
    public Chromosome mutate(Chromosome c)
    {
        Random box = new Random();
        int randomTuple1 = box.nextInt(0, chromosomeLength);

        //child mutation
        c.tuples[randomTuple1][0] = box.nextInt(0, courseDataSize);
        c.tuples[randomTuple1][1] = box.nextInt(0, roomDataSize);
        c.tuples[randomTuple1][2] = box.nextInt(0, timeDataSize);

        return c;
    }

    /**
     * Method generates random bitmask for crossover and/or mutations
     * @return the bitmask
     */
    public int[] generateMask()
    {

        int mask[] = new int[chromosomeLength];
        Random box = new Random();

        for (int i = 0; i < mask.length; i++)
        {
            mask[i] = box.nextInt(0,2);
        }
        return mask;
    }

    public static void main(String[] args){Menu m = new Menu();}
}