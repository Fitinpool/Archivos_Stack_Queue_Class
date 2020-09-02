
public class ChampId {

    private String BanId = "";
    private String ChampId = "";

    public ChampId()
    {

    }
    public ChampId(String ChampId, String BanId)
    {
        this.ChampId = ChampId;
        this.BanId = BanId;
    }
    
    public String GetBanId()
    {
        return(this.BanId);
    }

    public String GetChampId()
    {
        return(this.ChampId);
    }
}
