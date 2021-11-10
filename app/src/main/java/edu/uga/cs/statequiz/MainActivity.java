package edu.uga.cs.statequiz;
/**
 * Aviel Sabbag & Kevan Kadkhodaian
 * MainActivity class to house all necessary data for the quiz and options to go to tutorial, past quizzes
 */
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static CapitalsData capitalsData;
    public static List<CapitalQuizQuestion> capitalQuizQuestions;
    public static Quiz quiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        capitalsData = new CapitalsData(this);
        capitalsData.open();
        capitalsData.emptyDatabase(" quizzes");
        capitalsData.emptyDatabase(" capitals");
        capitalQuizQuestions = new ArrayList<CapitalQuizQuestion>();
        constructDatabase("state_caps2.csv");
        int questionNumber = 0;
        //if(!(savedInstanceState == null)) {
            //questionNumber = savedInstanceState.getInt("questionNum", 0);
        //}

        final FragmentContainerView fragContainer = (FragmentContainerView) findViewById(R.id.fragmentContainerView);
        SplashFragment splash = SplashFragment.newInstance(questionNumber, capitalQuizQuestions, this);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, splash).commit();
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBarDrawerToggle abDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.closed);
        drawerLayout.addDrawerListener(abDrawerToggle);
        abDrawerToggle.syncState();

        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                drawerLayout.closeDrawers();
                int id = menuItem.getItemId();

                if(id == R.id.home) {

                    SplashFragment splash = SplashFragment.newInstance(questionNumber, capitalQuizQuestions, getBaseContext());
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainerView, splash).commit();
                    return true;
                } else if (id == R.id.tutorial) {
                    OverviewFragment tutorial = OverviewFragment.newInstance(questionNumber);
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainerView, tutorial).commit();
                    return true;
                } else if (id == R.id.pastQuizzes) {
                    CapitalDBQuizReader newQuizReader = new CapitalDBQuizReader();
                    newQuizReader.execute();
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate menu
        getMenuInflater().inflate(R.menu.menu_nav_simple_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void constructDatabase(String fileName) {
            try {
                InputStream in_s = getAssets().open(fileName);
                CSVReader reader = new CSVReader(new InputStreamReader(in_s));
                String[] nextLine;
                capitalsData.open();
                String[] tableHeaders = reader.readNext();
                while ((nextLine = reader.readNext()) != null) {
                    CapitalQuizQuestion cqc = new CapitalQuizQuestion(nextLine[0], nextLine[1], nextLine[2], nextLine[3]);
                    new CapitalDBWriter().execute(cqc);
                }
            } catch (Exception e) {
                Log.e("DBConstruction", e.toString());
            }
        }

        public class CapitalDBWriter extends AsyncTask<CapitalQuizQuestion,
                CapitalQuizQuestion> {
            @Override
            protected CapitalQuizQuestion doInBackground(CapitalQuizQuestion... cqc) {
                capitalsData.storeQuizQuestion(cqc[0]);
                return cqc[0];
            }
            @Override
            protected void onPostExecute( CapitalQuizQuestion cqc) {
                capitalQuizQuestions.add(cqc);
            }
        }

    public class CapitalDBQuizReader extends AsyncTask<List<Quiz>,
            List<Quiz>> {
        @Override
        protected List<Quiz> doInBackground(List<Quiz>... cqc) {
            return capitalsData.retrieveAllQuizzes();
        }
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPostExecute( List<Quiz> cqc) {
            ReviewQuizzesFragment review = ReviewQuizzesFragment.newInstance(cqc, getBaseContext());
            FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
            fragmentTransaction1.replace(R.id.fragmentContainerView, review).commit();

        }
    }
}