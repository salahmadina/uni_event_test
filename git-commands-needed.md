# 👥 TEAM COLLABORATION GUIDE
## How to Clone, Branch, Push Code - Using VS Code Source Control

# STEP 1: CLONE THE REPOSITORY

## What is Cloning?
Cloning = Downloading the entire project to your computer.

## How to Clone?

### Method 1: Using VS Code (Easiest)

1. **Open VS Code**
   - Click: `View` → `Command Palette` (or press `Ctrl+Shift+P`)

2. **Search for Git Clone**
   - Type: `Git: Clone`
   - Press Enter

3. **Paste Repository URL**
   - Ask your team lead for the repository URL
   - It looks like: `https://github.com/salahmadina/uni_event_test.git`
   - Paste it and press Enter

4. **Choose Location**
   - Select where to save on your computer
   - VS Code will download the entire project
   - Wait for download to complete (1-2 minutes)

5. **Open Project**
   - Click `Open` to open the project
   - Now you have entire project on your computer ✅

---

### Method 2: Using Terminal (If First Method Doesn't Work)

1. Open Terminal in VS Code
   - `View` → `Terminal` (or press `Ctrl+```)

2. Run command:
   ```bash
   git clone https://github.com/salahmadina/uni_event_test.git
   ```

3. Navigate to project:
   ```bash
   cd uni_event_test
   ```

4. Open in VS Code:
   ```bash
   code .
   ```

---

## ✅ After Cloning

Verify you have:
- [ ] Project folder open in VS Code
- [ ] `src/` folder visible
- [ ] `pom.xml` file visible
- [ ] In Source Control panel, no changes shown (clean slate)

---

---

# STEP 2: CREATE YOUR OWN BRANCH

## What is a Branch?
- **main/master branch** = Official code (production)
- **Your branch** = Your personal workspace where you make changes
- Nobody else sees your changes until you create a Pull Request

## Branch Naming Convention
Name your branch: `firstname-lastname` or `firstname`

Examples:
- `john-smith`
- `sarah`
- `ahmed-ali`

---

## How to Create a Branch in VS Code?

### Method 1: Click on Branch Name (Easiest)

1. **Look at bottom-left of VS Code**
   - You should see: `main` or `master` (the current branch)

2. **Click on the branch name**
   - A dropdown appears showing available branches
   - Click: `+ Create new branch...`

3. **Enter Your Branch Name**
   - Type: your name (e.g., `john-smith`)
   - Press Enter

4. **Select Base Branch**
   - Choose: `main` 
   - Press Enter

5. **Success! ✅**
   - Bottom-left now shows your branch name
   - You're now on YOUR personal branch

---

### Method 2: Using Source Control Panel

1. **Click Source Control icon** (left sidebar)
   - It looks like a circle with 3 lines

2. **Click the three dots** (...) menu at top

3. **Select: `Branch` → `Create Branch`**

4. **Enter your branch name** and select base branch

5. **Success!** Your new branch created

---

## ✅ Verify You're on Your Branch

Look at **bottom-left corner** of VS Code:
- Should see YOUR branch name (not `main`)
- Example: `john-smith`

If you see `main` → you haven't switched to your branch yet!

---

---

# STEP 3: MAKE CHANGES & COMMIT

## What is a Commit?
A commit = Saving your changes with a message explaining what you did.

---

## How to Commit Changes?

### Step 1: Edit Code
- Open any file and make changes
- Example: Edit `User.java`, add a new field

### Step 2: See Changes in Source Control

1. **Click Source Control icon** (left sidebar)
   - Shows list of files you changed
   - Files have indicators:
     - `M` = Modified
     - `U` = Untracked (new file)

2. **Stage Changes** (Prepare to commit)
   - **Option A**: Stage all changes
     - Click `+` icon next to "Changes"
     - All files move to "Staged Changes"
   
   - **Option B**: Stage specific file
     - Click `+` icon next to that file
     - Only that file moves to "Staged Changes"

3. **Write Commit Message**
   - Find text box that says: "Message"
   - Type what you did (clear and short!)
   - Examples:
     - `Add email validation to User`
     - `Fix booking seat calculation`
     - `Update login page styling`

4. **Commit**
   - Click checkmark icon (✓) to commit
   - Or press `Ctrl+Enter`

5. **Success! ✅**
   - Changes saved with your message
   - Files disappear from Source Control panel

---

## Good Commit Messages

**Good examples:**
- ✅ `Add phone number field to User.java`
- ✅ `Fix double-booking bug in BookingService`
- ✅ `Update dashboard styling`

**Bad examples:**
- ❌ `Fix stuff`
- ❌ `Changes`
- ❌ `aaa`

**Why?** Future team members (including you!) need to understand what each commit does.

---

---

# STEP 4: PUSH YOUR CODE TO YOUR BRANCH

## What is Push?
Push = Upload your commits to GitHub/GitLab so others can see it.

---

## How to Push?

### Method 1: Using Source Control Panel (Easiest)

1. **Click Source Control icon** (left sidebar)

2. **Look for: "Publish Branch" or "Sync Changes"**

3. **First push?**
   - Click: `Publish Branch`
   - Your branch uploads to GitHub

4. **Not first push?**
   - Click: `Sync Changes` or up arrow icon
   - Your new commits upload to GitHub

5. **Success! ✅**
   - See message: "Published branch" or similar
   - Your code now on GitHub

---

### Method 2: Using Command Palette

1. **Open Command Palette** (`Ctrl+Shift+P`)

2. **Type**: `Git: Push`

3. **Press Enter**

4. **Enter credentials if asked**
   - GitHub username/email
   - Personal access token (ask team lead if needed)

5. **Success!** Code pushed to GitHub

---

### Method 3: Using Terminal

If above methods don't work:

1. **Open Terminal** (`Ctrl+```)

2. **First push** (creating your branch on GitHub):
   ```bash
   git push -u origin your-branch-name
   ```
   Example: `git push -u origin john-smith`

3. **Regular push** (after first time):
   ```bash
   git push
   ```

4. **Enter credentials if asked**

---

## ✅ Verify Your Code Uploaded

1. **Go to GitHub/GitLab website**
   - Open: https://github.com/username/uni_event_test

2. **Look for your branch**
   - Click: `Branch` dropdown
   - Should see your branch name (e.g., `john-smith`)

3. **Click your branch**
   - See your commits and changed files
   - Confirm your code is there ✅

---

---

# STEP 5: CREATE A PULL REQUEST (PR)

## What is a Pull Request?
A Pull Request = Asking your team to review your code before merging to `main`

---

## How to Create PR?

### Method 1: Using GitHub Website (Easiest)

1. **Go to GitHub**
   - https://github.com/username/uni_event_test

2. **Look for notification banner**
   - GitHub shows: "Your branch has recent pushes"
   - Click: `Compare & pull request`

3. **Fill PR Details**
   - **Title**: What did you do? (e.g., "Add email validation")
   - **Description**: Explain your changes
     - What did you change?
     - Why?
     - Any issues fixed?

4. **Click: `Create pull request`**

5. **Success! ✅**
   - PR created
   - Team members see notification
   - They review your code

---

### Method 2: If Banner Doesn't Appear

1. **Go to GitHub**

2. **Click: `Pull Requests` tab** (top menu)

3. **Click: `New pull request`** (green button)

4. **Select branches**
   - **base**: `main`
   - **compare**: your branch (e.g., `john-smith`)

5. **Click: `Create pull request`**

6. **Fill details** (title, description)

7. **Click: `Create pull request`** (again)

---

## Good Pull Request Description

```
## What does this PR do?
Added email validation to user registration

## Changes:
- Modified UserService.java to validate email format
- Added error message if email invalid
- Updated register.html to show error

## Testing:
- Tested with valid email: ✅ works
- Tested with invalid email: ✅ shows error
- No breaking changes to existing code

Fixes #123 (if there's a related issue)
```

---

---

# DAILY WORKFLOW SUMMARY

## Every Day You Work:

```
1. Open VS Code
   ↓
2. Make sure you're on YOUR branch (check bottom-left)
   ↓
3. Edit code files
   ↓
4. Commit changes:
   - Stage files
   - Write message
   - Click checkmark
   ↓
5. Push changes:
   - Click "Sync Changes" or "Publish Branch"
   ↓
6. (Optional) Create PR when done:
   - Go to GitHub
   - Create Pull Request
   - Team reviews and merges
```

---

---

# COMMON TASKS

## I want to switch back to main branch
1. Click branch name (bottom-left)
2. Select `main`
3. VS Code switches branches

## I made mistake, want to undo commit
1. Source Control → `Undo Last Commit` (from menu)
2. Or use terminal: `git reset --soft HEAD~1`

## I want to update my branch with latest main
1. Source Control → (three dots menu) → `Pull`
2. Or terminal: `git pull origin main`

## I need to see what changed
1. Click Source Control icon
2. Click file to see diff (additions highlighted in green, deletions in red)

## I want to discard my changes
1. Source Control → right-click file → `Discard Changes`
2. Or terminal: `git checkout -- filename`

---

---

# 🆘 TROUBLESHOOTING

## Push fails with "authentication"
- **Problem**: GitHub doesn't recognize you
- **Solution**: 
  1. Go to GitHub → Settings → Personal Access Tokens
  2. Create new token (ask team lead for help)
  3. VS Code will ask for token, paste it
  4. Try push again

## I see "origin/main is ahead of your branch"
- **Problem**: main branch has changes you don't have
- **Solution**:
  1. Source Control → (menu) → `Pull`
  2. Resolves conflicts if any
  3. Try push again

## My branch won't push
- **Problem**: Could be several things
- **Solution**:
  1. Make sure you committed changes
  2. Make sure you're on YOUR branch (not main)
  3. Try `git pull` first (get latest)
  4. Then try `git push` again

## I accidentally pushed to main instead of my branch
- **Tell team lead immediately!**
- Don't panic, it's fixable
- They can help undo it

---

---

# ✅ QUICK CHECKLIST

### First Time Setup:
- [ ] Cloned repository
- [ ] Created your branch (with your name)
- [ ] Verified you're on YOUR branch
- [ ] Made a test change
- [ ] Committed change
- [ ] Pushed change
- [ ] Verified on GitHub

### Every Time You Work:
- [ ] You're on YOUR branch (not main)
- [ ] You committed your changes (every 1-2 hours)
- [ ] You pushed your changes (at end of day)
- [ ] You wrote clear commit messages

### Before Creating PR:
- [ ] Code compiles without errors
- [ ] You tested your changes
- [ ] Commit messages are clear
- [ ] You're on YOUR branch

---

---

# 🆘 GET HELP

If something doesn't work:
1. **Ask team lead** (in person or Slack/Teams)
2. **Show them** what error message you see
3. **Tell them** what you were trying to do
4. They can help or pair with you

**Common solution**: 
- Most issues = just need to pull latest code or re-authenticate

---

**Happy coding! 🚀**
