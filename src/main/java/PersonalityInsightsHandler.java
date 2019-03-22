import com.ibm.watson.developer_cloud.personality_insights.v3.PersonalityInsights;
import com.ibm.watson.developer_cloud.personality_insights.v3.model.Profile;
import com.ibm.watson.developer_cloud.personality_insights.v3.model.ProfileOptions;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class PersonalityInsightsHandler {

    public static void main (String[] args) {
        sendToPersonalityInsights("\n" +
                "\n" +
                        "I would like to make some tools available for my lab-workers. Usually, I develop and use my tools in jupyter notebooks. Though, big parts of my workgroup run Windows and require easy to use tools. There is a really nice tool called appmode for jupyter notebooks that hides all code and only shows app controls and output. Now, I could reimplement everything and setup a flask app, but essentially the notebook provides already everything needed. Though, it still requires users to start the notebook server etc. I wonder how I can deliver a jupyter notebook so that the user can basically click on an icon.\n" +
                        "\n" +
                        "Then the a jupyter notebook using an app-specific conda environment should be started.\n" +
                        "\n" +
                        "I am way more familiar with programming in Linux environments. So, this kind of tasks give me headaches.\n)", 12345);
    }

    public static void sendToPersonalityInsights(String text, int userId) {
        try {
            FileWriter fileWriter = new FileWriter(Integer.toString(userId) + ".json");
            PersonalityInsights service = new PersonalityInsights("2016-10-19");

            ProfileOptions options = new ProfileOptions.Builder()
                    .text(text)
                    .build();

            Profile profile = service.profile(options).execute();
            fileWriter.write(profile.toString());
            System.out.println(profile);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

}
