package com.example.student.charactersheet5e;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import AppModels.CharSheet;
import IO.WriteObject;

public class ReviewCAC extends AppCompatActivity
{
    private TextView raceModsListTextView;

    private TextView strScoreText;
    private TextView dexScoreText;
    private TextView conScoreText;
    private TextView intScoreText;
    private TextView wisScoreText;
    private TextView chaScoreText;

    private TextView strModText;
    private TextView dexModText;
    private TextView conModText;
    private TextView intModText;
    private TextView wisModText;
    private TextView chaModText;

    private CharSheet charSheet;

    private int[] abilityBonus;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_cac);



        //Connect variables to layout
        raceModsListTextView = findViewById(R.id.raceModsListText);

        strScoreText = findViewById(R.id.strength_score_text);
        dexScoreText = findViewById(R.id.dexterity_score_text);
        conScoreText = findViewById(R.id.constitution_score_text);
        intScoreText = findViewById(R.id.intelligence_score_text);
        wisScoreText = findViewById(R.id.wisdom_score_text);
        chaScoreText = findViewById(R.id.charisma_score_text);

        strModText = findViewById(R.id.strength_mod_disp_text);
        dexModText = findViewById(R.id.dexterity_mod_disp_text);
        conModText = findViewById(R.id.constitution_mod_disp_text);
        intModText = findViewById(R.id.intelligence_mod_disp_text);
        wisModText = findViewById(R.id.wisdom_mod_disp_text);
        chaModText = findViewById(R.id.charisma_mod_disp_text);

        TextView hitDieText = findViewById(R.id.hitDie_text);
        TextView hitPointsText = findViewById(R.id.hitPoints_text);

        ImageButton navigate_to_next = findViewById(R.id.navigate_to_next_CAC03);
        Button helpButton = findViewById(R.id.reviewHelpButton);


        //Set a new character sheet from the old one ( may be a better way to do this?? )
        charSheet = (CharSheet) (getIntent().getSerializableExtra("characterSheet"));

        //Initialize array to hold bonuses for race and subrace
        abilityBonus = new int[] {0, 0, 0, 0, 0, 0};

        //Insert a list of mods retrieved from Race into race mods list text
        String modsListText = GetRaceModsList(charSheet.getCharRace().getRaceName());

        //If they have a subrace
        if( !charSheet.getCharRace().getSubraceName().equals("None"))
        {
            //Change action bar text
            getSupportActionBar().setTitle("Review Ability Scores: " + charSheet.getCharRace().getSubraceName());

            //Insert a list of mods retrieved from SubRace into race mods list text
            modsListText += GetSubraceModsList(charSheet.getCharRace().getSubraceName());
        }else
        {
            //Change action bar text
            getSupportActionBar().setTitle("Review Ability Scores: " + charSheet.getCharRace().getRaceName() );

        }

        raceModsListTextView.setText(modsListText);

        SetAbilityScore();

        //Assuming lvl 1 set hit die
        int hitDieAmt = GetHitDie(charSheet.getCharClass().getClassName());
        String hitDieString = "1 X D" + hitDieAmt;
        hitDieText.setText(hitDieString);
        charSheet.getCharStats().setHitDie(hitDieString);

        //Assuming lvl 1 set hit points
        int hitPoints = hitDieAmt + abilityBonus[2];
        String hitPointsString = hitPoints + " ";
        hitPointsText.setText(hitPointsString);
        charSheet.getCharStats().setHitpoints(hitPoints);

        //The speed of the character
        charSheet.getCharStats().setSpeed(GetSpeed(charSheet.getCharRace().getRaceName()));

        //Help button
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplicationContext(), Pop_ReviewHelp.class);
                startActivity(i);
            }
        });

        navigate_to_next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(ReviewCAC.this, ProficienciesCAC.class);

                //send the character sheet to the next activity to add scores
                intent.putExtra("characterSheet", charSheet);

                startActivity(intent);
            }
        });


    }

    private void SetAbilityScore() {
        //Create an array with all ability scores
        int[] abilityScores;
        abilityScores = new int[] {charSheet.getCharStats().getStrength()+ abilityBonus[0],
                charSheet.getCharStats().getDexterity()+ abilityBonus[1],
                charSheet.getCharStats().getConstitution() + abilityBonus[2],
                charSheet.getCharStats().getIntelligence()+ abilityBonus[3],
                charSheet.getCharStats().getWisdom()+ abilityBonus[4],
                charSheet.getCharStats().getCharisma()+ abilityBonus[5]};

        strScoreText.setText(Integer.toString(abilityScores[0] ));
        dexScoreText.setText(Integer.toString(abilityScores[1] ));
        conScoreText.setText(Integer.toString(abilityScores[2]));
        intScoreText.setText(Integer.toString(abilityScores[3] ));
        wisScoreText.setText(Integer.toString(abilityScores[4] ));
        chaScoreText.setText(Integer.toString(abilityScores[5] ));

        if (0 < ((abilityScores[0]/2) - 5))
        {
            strModText.setText( "+" + Integer.toString( (abilityScores[0]/2) - 5) );
        }else
        {
            strModText.setText( Integer.toString( (abilityScores[0]/2) - 5) );
        }
        if (0 < ((abilityScores[1]/2) - 5))
        {
            dexModText.setText( "+" + Integer.toString( (abilityScores[1]/2) - 5) );
        }else
        {
            dexModText.setText( Integer.toString( (abilityScores[1]/2) - 5) );
        }
        if (0 < ((abilityScores[2]/2) - 5))
        {
            conModText.setText( "+" + Integer.toString( (abilityScores[2]/2) - 5) );
        }else
        {
            conModText.setText( Integer.toString( (abilityScores[2]/2) - 5) );
        }
        if (0 < ((abilityScores[3]/2) - 5))
        {
            intModText.setText( "+" + Integer.toString( (abilityScores[3]/2) - 5) );
        }else
        {
            intModText.setText( Integer.toString( (abilityScores[3]/2) - 5) );
        }
        if (0 < ((abilityScores[4]/2) - 5))
        {
            wisModText.setText( "+" + Integer.toString( (abilityScores[4]/2) - 5) );
        }else
        {
            wisModText.setText( Integer.toString( (abilityScores[4]/2) - 5) );
        }if (0 < ((abilityScores[5]/2) - 5))
        {
            chaModText.setText( "+" + Integer.toString( (abilityScores[5]/2) - 5) );
        }else
        {
            chaModText.setText( Integer.toString( (abilityScores[5]/2) - 5) );
        }
    }

    private String GetRaceModsList(String raceName)
    {
        String modsList = new String();
        try
        {
            InputStream inStream = getAssets().open("races_5e.json");
            int size = inStream.available();
            byte[] buffer = new byte[size];
            inStream.read(buffer);
            inStream.close();

            String json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);

            for (int i =0; i < jsonArray.length(); i++)
            {
                JSONObject obj = jsonArray.getJSONObject(i);

                if (obj.getString("name").equals(raceName))
                {
                    JSONArray attributeJsonArray = obj.getJSONArray("ability_bonuses");
                    for (int index =0; index < attributeJsonArray.length(); index++)
                    {
                        JSONObject attributeObj = attributeJsonArray.getJSONObject(index);
                        modsList += attributeObj.getString("name") + " (+" +
                                attributeObj.getString("bonus") + ")\t\t";
                        SetBonusArray(attributeObj.getString("name"), attributeObj.getInt("bonus"));
                        if ((index + 1)%2 == 0)
                        {
                            modsList += "\n";
                        }
                    }
                }
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        return modsList;
    }

    private String GetSubraceModsList(String subraceName)
    {
        String modsList = new String();
        try
        {
            InputStream inStream = getAssets().open("subraces_5e.json");
            int size = inStream.available();
            byte[] buffer = new byte[size];
            inStream.read(buffer);
            inStream.close();

            String json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);

            for (int i =0; i < jsonArray.length(); i++)
            {
                JSONObject obj = jsonArray.getJSONObject(i);

                if (obj.getString("name").equals(subraceName))
                {
                    JSONArray attributeJsonArray = obj.getJSONArray("ability_bonuses");
                    for (int index =0; index < attributeJsonArray.length(); index++)
                    {
                        JSONObject attributeObj = attributeJsonArray.getJSONObject(index);
                        modsList += attributeObj.getString("name") + " (+" +
                                attributeObj.getString("bonus") + ")\t\t";
                        SetBonusArray(attributeObj.getString("name"), attributeObj.getInt("bonus"));
                        if ((index + 1)%3 == 0)
                        {
                            modsList += "\n";
                        }
                    }
                }
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        return modsList;
    }

    private void SetBonusArray(String name, int bonus) {
        if(name.equals("STR"))
        {
            abilityBonus[0] +=  bonus;
        }
        if(name.equals("DEX"))
        {
            abilityBonus[1] +=  bonus;
        }
        if(name.equals("CON"))
        {
            abilityBonus[2] +=  bonus;
        }
        if(name.equals("INT"))
        {
            abilityBonus[3] +=  bonus;
        }
        if(name.equals("WIS"))
        {
            abilityBonus[4] +=  bonus;
        }
        if(name.equals("CHA"))
        {
            abilityBonus[5] +=  bonus;
        }
    }

    private int GetHitDie(String className)
    {
        int hitDieText = 0;

        try
        {
            //Open the file
            InputStream inStream = getAssets().open("classes_5e.json");
            int size = inStream.available();
            byte[] buffer = new byte[size];
            inStream.read(buffer);
            inStream.close();

            String json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);

            for (int i =0; i < jsonArray.length(); i++)
            {
                //Add class name to list of classes
                JSONObject obj = jsonArray.getJSONObject(i);

                if (obj.getString("name").equals(className))
                {
                    hitDieText = obj.getInt("hit_die");
                    return hitDieText;
                }
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }

        return hitDieText;
    }

    private int GetSpeed(String raceName)
    {
        int speed = 0;

        try
        {
            //Open the file
            InputStream inStream = getAssets().open("races_5e.json");
            int size = inStream.available();
            byte[] buffer = new byte[size];
            inStream.read(buffer);
            inStream.close();

            String json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);

            for (int i =0; i < jsonArray.length(); i++)
            {
                //Add class name to list of classes
                JSONObject obj = jsonArray.getJSONObject(i);

                if (obj.getString("name").equals(raceName))
                {
                    speed = obj.getInt("speed");
                    return speed;
                }
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }

        return speed;
    }
}


