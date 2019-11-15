package com.example.loginroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.loginroom.db.DBRepo;
import com.example.loginroom.db.DBTimestamp;
import com.example.loginroom.db.DBUser;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements
        LoginFragment.OnFragmentInteractionListener,
        ContentFragment.OnFragmentInteractionListener,
        AddUserFragment.OnFragmentInteractionListener {

    private boolean loggedIn = false;
    private MyDatabase myDatabase;
    private DBRepo dbRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        myDatabase = MyDatabase.getInstance(getApplicationContext());
        dbRepo = new DBRepo(getApplicationContext());


        Fragment loginFragment = new LoginFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(R.id.fragment_placeholder, loginFragment);
        ft.commit();
    }

    // Implement methods for interface on all three fragments.
    @Override
    public void onFragmentInteraction(View view) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch(view.getId()) {
            case R.id.buttonLogin: // At login screen
                Fragment contentFragment =  new ContentFragment();
                ft.replace(R.id.fragment_placeholder, contentFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case R.id.buttonReg: // Add new user at login screen
                Fragment addUserFragment = new AddUserFragment();
                ft.replace(R.id.fragment_placeholder, addUserFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case R.id.buttonLogout:
                loggedIn = false;
            case R.id.buttonBack: // Back from add new user screen
                FragmentManager fm = getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    fm.popBackStack();
                }
                break;

        }

    }

    // On login button
    @Override
    public void loginUser(String user, String pass) {

        int ok = dbRepo.loginUser(user, pass);
        Log.i("OMA", "logged in uid: " + ok);

        if (ok > 0) {
            loggedIn = true;
            List<DBTimestamp> timestamps = dbRepo.getTimesByUid(ok);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Fragment contentFragment = ContentFragment.newInstance(timestamps);
            ft.replace(R.id.fragment_placeholder, contentFragment);
            ft.addToBackStack(null);
            ft.commit();
        }
    }

    // Add new user button clicked, R.id.buttonAddUser
    @Override
    public void onAddUser(String user, String pass) {
        DBUser dbUser = new DBUser();

        dbUser.username = user;
        dbUser.password = pass;

        Log.i("OMA", "counting user: " + user);
        int count = dbRepo.getUserCountByName(user);
        if (count > 0) {
            Log.i("OMA", "user count is " + count);
            Toast.makeText(getApplicationContext(), "User already exists.", Toast.LENGTH_SHORT).show();
        }
        else {
            Log.i("OMA", "adding new user " + user);
            addUser(dbUser);
            //dbRepo.insertUser(dbUser);
            Toast.makeText(getApplicationContext(), "New user added.", Toast.LENGTH_SHORT).show();
        }

        /* LiveData
        final LiveData<Integer> count = dbRepo.getUserCountByName(user);
        count.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer count) {
                if (count > 0) {
                    Log.i("OMA", "user count is " + count.toString());
                    Toast.makeText(getApplicationContext(), "User already exists,", Toast.LENGTH_SHORT).show();
                }
            }
        });
*/
    }

    public void addUser(final DBUser dbUser) {

        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                dbRepo.insertUser(dbUser);
            }
        });
    }

}
