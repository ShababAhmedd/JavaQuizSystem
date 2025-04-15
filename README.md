# Java Quiz System
A simple Java-based command-line quiz application where users can log in as either **admin** or **student**.

- **Admins** can add new multiple-choice questions to a quiz bank.
- **Students** can take a 10-question quiz generated randomly from the question bank.

All user and question data is managed using **JSON files**.

## Technologies Used

- **Java** (version 17)
- **Gradle** (build system)
- **JSON.simple** (for handling JSON files)
- **IntelliJ IDEA** (IDE)


## Features

- User login system using `users.json`
- Admin role can:
  - Add new quiz questions
  - Input 4 options and specify the correct answer
- Student role can:
  - Take a 10-question quiz with randomized questions
  - Get instant score feedback
  - Retry the quiz if desired
- Data is stored in:
  - `users.json` for user credentials and roles
  - `quizBank.json` for quiz questions

## Setup Instructions

Follow these steps to set up and run the project locally on your machine.

### 1. Clone the Repository

```bash
git clone https://github.com/ShababAhmedd/JavaQuizSystem.git
cd JavaQuizSystem
```

### 2. Open the Project in IntelliJ IDEA

- Launch IntelliJ IDEA.
- Go to **File > Open**.
- Select the root folder of the cloned project (`JavaQuizSystem`) and open it.


### 3. Set the Correct JDK

- Navigate to **File > Project Structure > Project**.
- Set **Project SDK** to **Oracle OpenJDK 17.0.12** (or add it manually if not available).
- Ensure the **Project language level** is set to **17**.\


### 4. Confirm Gradle and Groovy DSL

- This project uses **Gradle** as the build system with the **Groovy DSL**.
- Ensure the **build.gradle** file is present and properly configured.


### 5. Add JSON.simple Dependency (Already Configured)

- Open the `build.gradle` file and confirm the following dependency exists under **dependencies**:

```groovy
dependencies {
    // https://mvnrepository.com/artifact/com.googlecode.json-simple/json-simple
    implementation("com.googlecode.json-simple:json-simple:1.1.1")
}
```


### 6. Sync Gradle

- Open the **Gradle** tab on the right sidebar of IntelliJ IDEA.
- Click the **Reload All Gradle Projects** button (🔄) to sync dependencies.
- Alternatively, use **Build > Reload Project** from the top menu.


### 7. Run the Application

- Navigate to the file: `src/main/java/JavaQuizSystem/JavaQuizSystem.java`.
- Right-click on the file and select **Run 'JavaQuizSystem.main()'**.
- Follow the on-screen instructions to use the quiz system.


## Video Demonstrations
### Admin Login
Watch the video below to see how the **Admin** logs in and accesses the quiz system.
<a href="https://youtu.be/EsrnJkhmPYg" target="_blank">
  <img src="https://img.youtube.com/vi/EsrnJkhmPYg/0.jpg" alt="Admin Login Demonstration" width="200"/>
</a>

### Student Login
Watch the video below to see how the **Student** logs in and accesses the quiz system.
<a href="https://youtu.be/HyQ_S8HO2Mk" target="_blank">
  <img src="https://img.youtube.com/vi/EsrnJkhmPYg/0.jpg" alt="Student Login Demonstration" width="200"/>
</a>
