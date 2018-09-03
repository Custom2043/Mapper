package main;

public class FFT
{
	public double[][] valeurs;
	public int size;
	
	/**
	 * Initializes the FFT
	 * @param size : Must be a power of 2 (not less than 2)
	 */
	public FFT(int size)
	{
		this.size = size;
		prepareValues();
	}
	
	
	public double[] fft(double[] entree)
	{
		//On consid�re entr�e multiple de 2
		double[] sortie = new double[entree.length / 2 + 1];
		
		for (int i=0;i<entree.length/4;i++)
		{
			double pair = 0, impair = 0;
			
			for (int j=0;j<entree.length;j+=2)
				pair += entree[j] * valeurs[i][j]; // Pair
			
			for (int j=1;j<entree.length;j+=2)
				impair += entree[j] * valeurs[i][j]; // Impair
			
			sortie[i] = (pair + impair) / entree.length;
			sortie[sortie.length - i - 1] = (pair - impair) / entree.length;
			
			if (i != 0)
			{
				sortie[i] *= 2;
				sortie[sortie.length - i - 1] *= 2;
			}
		}
		
		for (int j=0;j<size;j++)
			sortie[entree.length/4] += entree[j] * valeurs[entree.length/4][j] * 2;
		
		sortie[entree.length/4] /=  entree.length;
		
		return sortie;
	}
	
	public float[] fft(float[] entree)
	{
		//On consid�re entr�e multiple de 2
		float[] sortie = new float[entree.length / 2 + 1];
		
		for (int i=0;i<entree.length/4;i++)
		{
			double pair = 0, impair = 0;
			
			for (int j=0;j<entree.length;j+=2)
				pair += entree[j] * valeurs[i][j]; // Pair
			
			for (int j=1;j<entree.length;j+=2)
				impair += entree[j] * valeurs[i][j]; // Impair
			
			sortie[i] = (float)(pair + impair) / entree.length;
			sortie[sortie.length - i - 1] = (float)(pair - impair) / entree.length;
			
			if (i != 0)
			{
				sortie[i] *= 2;
				sortie[sortie.length - i - 1] *= 2;
			}
		}
		
		for (int j=0;j<size;j++)
			sortie[entree.length/4] += entree[j] * valeurs[entree.length/4][j] * 2;
		
		sortie[entree.length/4] /=  entree.length;
		
		return sortie;
	}
	
	private void prepareValues()
	{
		valeurs = new double[size][size];
		
		for (int i=0;i<valeurs[0].length;i++)
		{
			valeurs[0][i] = 1;
			valeurs[i][0] = 1;
		}
		
		for (int i=1;i<size / 4;i++)
			valeurs[1][i] = Math.cos(2 * Math.PI * i / size);
		valeurs[1][size/4] = 0;
		
		for (int i=size/4+1;i<size / 2;i++)
			valeurs[1][i] = -valeurs[1][size/2 - i];
		valeurs[1][size/2] = -1;
		
		for (int i=size/2+1;i<size;i++)
			valeurs[1][i] = valeurs[1][size - i];

		for (int i=2;i<size;i++)
			for (int j=1;j<size;j++)
				valeurs[i][j] = valeurs[1][(i * j)%size];
	}
}
