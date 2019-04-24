package AppModels;

public class CharacterCardView {
    private int mPortrait;
    private String mRace;
    private String mName;
    private String mClass;
    private String mLvl;

    public CharacterCardView( int portrait, String name, String race, String charClass, String lvl)
    {
        mPortrait = portrait;
        mName = name;
        mRace = race;
        mClass = charClass;
        mLvl = lvl;

    }
    public void changeName(String text){
        mName = text;

    }

    public int getPortrait() {
        return mPortrait;
    }

    public void setPortrait(int mPortrait) {
        this.mPortrait = mPortrait;
    }

    public String getRace() {
        return mRace;
    }

    public void setRace(String mRace) {
        this.mRace = mRace;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getmClass() {
        return mClass;
    }

    public void setmClass(String mClass) {
        this.mClass = mClass;
    }

    public String getmLvl() {
        return mLvl;
    }

    public void setmLvl(String mLvl) {
        this.mLvl = mLvl;
    }
}
