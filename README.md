# DictionaryApp (Java + SQLite)

## Tong quan
Ung dung tu dien don gian cho Android, su dung Java va SQLite. Nguoi dung nhap tu, bam LOOKUP de tra nghia hoac goi y tu lien quan.

## Tinh nang
- Tra nghia chinh xac theo tu (khong phan biet hoa thuong)
- Goi y cac tu co chua chuoi tim kiem
- Hien thi thong bao khong tim thay
- Xu ly loi go tieng Viet tren emulator bang delay 150ms

## Thong so
- Language: Java
- Min SDK: 21
- Package: com.example.dictionaryapp
- DB: dictionary.db, version 2

## Cach chay
1. Mo project bang Android Studio.
2. Sync Gradle.
3. Run tren emulator hoac thiet bi that.

## Cach su dung
1. Nhap tu can tra vao o nhap.
2. Bam LOOKUP hoac nhan nut Search tren ban phim.
3. Xem nghia hoac danh sach goi y.

## AI Prompt da su dung
Duoi day la prompt duoc dung de tao code (giu nguyen noi dung):

```
You are an expert Android developer. Create a complete Dictionary Android App 
using Java and SQLite. Follow these exact requirements:

## Project Setup
- Language: Java
- Min SDK: API 21
- Template: Empty Views Activity
- Package: com.example.dictionaryapp

## App Requirements
1. User types a word → press LOOKUP button
2. If exact word exists in database → show its definition
3. If exact word does NOT exist → list all words that contain 
   the user's input as a substring
4. If no match at all → show "No results found"

## Files to Generate
Generate complete code for these 4 files:

### 1. res/layout/activity_main.xml
- LinearLayout vertical, padding 16dp, background #F5F5F5
- Title TextView "Dictionary", bold, color #1A237E, size 28sp
- Horizontal LinearLayout containing:
  - EditText id="editTextWord", weight=1, 
    inputType="text|textNoSuggestions",
    imeOptions="actionSearch|flagNoExtractUi|flagNoFullscreen"
  - Button id="btnLookup", text="LOOKUP", backgroundTint #1A237E
- TextView id="tvResult", visibility=gone
- ListView id="listViewSuggestions", visibility=gone

### 2. DatabaseHelper.java
- Extends SQLiteOpenHelper
- DB_NAME = "dictionary.db", DB_VERSION = 2
- Table: words (id INTEGER PRIMARY KEY AUTOINCREMENT, 
  word TEXT NOT NULL, definition TEXT NOT NULL)
- Seed at least 40 English words with definitions in onCreate()
  covering topics: programming, computer science, general vocabulary
- onUpgrade() must drop and recreate table

### 3. WordRepository.java
- Constructor receives Context
- Method getExactDefinition(String word): 
  uses LOWER() for case-insensitive exact match,
  returns String definition or null
- Method searchBySubstring(String query):
  uses LOWER() + LIKE for case-insensitive substring search,
  returns List<String> formatted as "word  —  definition"

### 4. MainActivity.java
- Extends AppCompatActivity
- Button click and EditorAction (IME_ACTION_SEARCH) both trigger lookup
- commitAndLookup() method:
  1. Call editTextWord.clearComposingText()
  2. Hide soft keyboard via InputMethodManager
  3. Use Handler().postDelayed(150ms) before reading text
     (fixes Vietnamese IME composing issue on emulator)
- lookupWord() method:
  1. Read and trim input
  2. Show Toast if empty
  3. Reset visibility of tvResult and listViewSuggestions
  4. Call getExactDefinition() → if found, show "📖 word\n\ndefinition"
  5. Else call searchBySubstring() → if found, show list with 
     "No exact match. Did you mean:" header
  6. Else show "❌ No results found for [input]"

## Important Notes
- All Cursor objects must be closed after use
- Use LOWER() on both column and input for case-insensitive search
- DB_VERSION = 2 so onUpgrade() runs automatically without uninstall
- Handler delay 150ms is required to fix IME commit timing issue
- Do NOT use deprecated Handler() constructor, 
  use new Handler(Looper.getMainLooper())

## Output Format
Provide each file separately with clear file path headers.
Include all necessary imports.
Add brief comments in Vietnamese explaining key logic.
```
