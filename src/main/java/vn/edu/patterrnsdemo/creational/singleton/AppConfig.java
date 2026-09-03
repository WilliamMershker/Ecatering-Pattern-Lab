package vn.edu.patterrnsdemo.creational.singleton;

public final class AppConfig
{

    private static volatile AppConfig instance;

    private final String databaseUrl;
    private final double vatRate;

    private AppConfig()
    {
        databaseUrl = "jdbc:postgresql://localhost:5432/my_db";
        vatRate = 0.08;
    }

    public static AppConfig getInstance()
    {
        if (instance == null)
        {
            synchronized (AppConfig.class)
            {
                if (instance == null)
                {
                    instance = new AppConfig();
                }
            }
        }

        return instance;
    }

    public String getDatabaseUrl()
    {
        return databaseUrl;
    }

    public double getVatRate()
    {
        return vatRate;
    }
}