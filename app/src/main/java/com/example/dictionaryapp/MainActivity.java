package com.example.dictionaryapp;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText  editTextWord;
    private TextView  tvResult;
    private ListView  listViewSuggestions;
    private WordRepository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextWord        = findViewById(R.id.editTextWord);
        tvResult            = findViewById(R.id.tvResult);
        listViewSuggestions = findViewById(R.id.listViewSuggestions);
        repo                = new WordRepository(this);

        Button btnLookup = findViewById(R.id.btnLookup);

        // Bấm nút LOOKUP
        btnLookup.setOnClickListener(v -> commitAndLookup());

        // Bấm phím Search/Enter trên bàn phím cũng trigger lookup
        editTextWord.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                commitAndLookup();
                return true;
            }
            return false;
        });
    }

    private void commitAndLookup() {
        // Bước 1: commit composing text của bộ gõ tiếng Việt
        editTextWord.clearComposingText();

        // Bước 2: ẩn bàn phím (trigger IME commit nốt phần còn lại)
        InputMethodManager imm =
                (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editTextWord.getWindowToken(), 0);

        // Bước 3: delay nhỏ để IME có thời gian commit xong rồi mới đọc text
        new Handler().postDelayed(() -> lookupWord(), 150);
    }

    private void lookupWord() {
        String input = editTextWord.getText().toString().trim();

        if (input.isEmpty()) {
            Toast.makeText(this, "Please enter a word", Toast.LENGTH_SHORT).show();
            return;
        }

        // Reset UI
        tvResult.setVisibility(View.GONE);
        listViewSuggestions.setVisibility(View.GONE);

        // Case 1: exact match
        String definition = repo.getExactDefinition(input);
        if (definition != null) {
            tvResult.setText("📖  " + input.toLowerCase() + "\n\n" + definition);
            tvResult.setVisibility(View.VISIBLE);
            return;
        }

        // Case 2: substring match
        List<String> suggestions = repo.searchBySubstring(input);
        if (suggestions.isEmpty()) {
            tvResult.setText("❌  No results found for \"" + input + "\"");
            tvResult.setVisibility(View.VISIBLE);
        } else {
            tvResult.setText("No exact match. Did you mean:");
            tvResult.setVisibility(View.VISIBLE);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    suggestions
            );
            listViewSuggestions.setAdapter(adapter);
            listViewSuggestions.setVisibility(View.VISIBLE);
        }
    }
}