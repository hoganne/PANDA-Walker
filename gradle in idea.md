# gradle in idea

### Open an existing Gradle project﻿

1. If no project is currently opened in IntelliJ IDEA, click

   Open on the welcome screen. Otherwise, select File | Open from the main menu.

   If you have some custom plugins that require you to import your project from the IntelliJ IDEA model, press Ctrl+Shift+A and search for the **Project from Existing Sources** action.

2. In the dialog that opens, select a directory containing a Gradle project and click OK

   IntelliJ IDEA opens and syncs the project in the IDE.

If you need to adjust the Gradle settings options, refer to [Gradle settings](https://www.jetbrains.com/help/idea/gradle-settings.html).

### Check Gradle JVM and language level﻿

- **Gradle JVM**: when IntelliJ IDEA opens the Gradle project, it checks the `gradle.properties` file for the appropriate JVM version specified in `org.gradle.java.home` and uses it for the project. If it is not specified, then the project SDK is used. Alternatively, you can use the [Gradle settings](https://www.jetbrains.com/help/idea/gradle-settings.html) to configure the Gradle JVM.

  当IntelliJ IDEA打开Gradle项目时，它会检查' Gradle。org.gradle.java. properties文件中指定的适当的JVM版本。并将其用于该项目。如果没有指定，则使用project SDK。或者，您可以使用[Gradle settings](https://www.jetbrains.com/help/idea/gradle-settings.html)来配置Gradle JVM。

- Language level: the language level settings are applied for a source root or for a module. If a Gradle project has a single  linked project then the project default language level is set to the minimum language level among the module language levels. The module language level is set to

   语言级别:语言级别设置应用于源根或模块。如果一个Gradle项目有一个单独的链接项目，那么项目的默认语言级别将被设置为模块语言级别中的最低语言级别。模块语言级别设置为

  ```
  sourceCompatibility
  ```

  in the build.gradle file.

  The preview part is set to the conjunction of preview flags of the module source sets. The source set module language level is set to the corresponding combination of `sourceCompatibility` property and `--enable-preview` flag.

### Link a Gradle project to an IntelliJ IDEA project﻿

You can have multiple Gradle projects inside one IntelliJ IDEA project. It might be helpful if you keep parts of code in different projects, have some legacy projects on which you need to work, have [Gradle composite build](https://www.jetbrains.com/help/idea/work-with-gradle-projects.html#gradle_composite_build) or work with [microservices](https://microservices.io/). You can link such projects in IntelliJ IDEA and manage them simultaneously.

When you [open](https://www.jetbrains.com/help/idea/gradle.html#gradle_import_project_start) a Gradle project, the link of the project is established automatically and the **Gradle** tool window is enabled.

If an IntelliJ IDEA project is not linked to a Gradle project, then the Gradle tool window is disabled. In this case, IntelliJ IDEA displays a message with a link that quickly lets you reimport your Gradle project and enable the **Gradle** tool window. If the **Gradle** tool window is active, then you have at least one Gradle project linked.

1. Open the **Gradle** tool window.
2. In the **Gradle** tool window, click ![the Link Gradle Project icon](https://www.jetbrains.com/help/img/idea/2020.3/icons.general.add.svg) to attach a Gradle project.
3. In the dialog that opens, select the desired **build.gradle** file, and click **OK**.
4. In the Import Module from Gradle window, specify options for the Gradle project that you are trying to link and click OK.The project is linked. The **Gradle** tool window shows the toolbar and a tree view of Gradle entities.

If you need to link back the previously [unlinked project](https://www.jetbrains.com/help/idea/work-with-gradle-projects.html#ignore_detach_gradle_project), in the **Project** tool window, right-click the added **build.gradle** or if it is a Gradle Kotlin module the **build.gradle.kts** file and select **Import Gradle Project**.

### Add a new Gradle module to an existing Gradle project﻿

You can add a Gradle module to a project in which you are already working.

1. In a project, from the main menu, select **File| New | Module** to open the **New Module** wizard.
2. If the existing project is not the Gradle project then the process of adding a module is the same as [Creating a new Gradle project](https://www.jetbrains.com/help/idea/gradle.html#gradle_create_project). If the existing project is a Gradle project then the process of adding a new module is shorter. You need to specify the name of your module in the **ArtifactId** field. The rest of the information is added automatically and you can use either the default settings or change them according to your preferences. Also, note that **Add as module to** field, by default, displays the name of your project to which you are trying to add a module. You can click ![Add module](https://www.jetbrains.com/help/img/idea/2020.3/icons.nodes.Module.svg) to select a different name if you have other linked Gradle projects.

### Convert a regular project into a Gradle project﻿

1. Open your project in IntelliJ IDEA.

2. In the **Project** tool window, right-click the name of your project and select **New | File**.

3. In the dialog that opens enter **build.gradle** and click **OK**.

4. Open the

    

   build.gradle

    

   file in the editor, add the information you need and re-open your project. The following minimal information should be included into the project's build script file:

   ```groovy
   plugins {
       id 'java'
   }
   
   group 'org.example'
   version '1.0-SNAPSHOT'
   
   repositories {
       mavenCentral()
   }
   sourceSets {
       main {
           java {
               srcDirs = ['src']
           }
       }
   }
   dependencies {
       compile 'junit:junit:4.12'
   }
   ```

   Copied!

   As soon as you create a **build.gradle** file, IntelliJ IDEA recognizes the Gradle build script and displays a notification suggesting to load the project as Gradle. After you load the project, IntelliJ IDEA enables the Gradle tool window.

   We also recommend that you add the **settings.gradle** file to your project and add `rootProject.name = 'projectName'` to it. Where `'projectName'` would be the name of your project.

### Access the Gradle settings﻿

Use the [Gradle settings](https://www.jetbrains.com/help/idea/gradle-settings.html) to configure the build and run actions for each linked Gradle project, a Gradle version, importing of the project's changes, and so on.

1. In the

    

   Settings/Preferences

    

   dialog

    

   Ctrl+Alt+S

    

   , go to

    

   Build, Execution, Deployment| Gradle

   .

   ![Gradle settings](https://www.jetbrains.com/help/img/idea/2020.3/gradle_settings_preferences.png)

   > ### 
   >
   > 
   >
   > Click ![Gradle Settings](https://www.jetbrains.com/help/img/idea/2020.3/icons.general.settings.svg) on the toolbar, in the **Gradle** tool window to access the Gradle settings.

2. On the Gradle settings page, configure the available options and click **OK** to save the changes.

### Configure a Gradle version for a project﻿

IntelliJ IDEA lets you use different options to configure a Gradle version for your Gradle project. You can use the default Gradle wrapper, use a Gradle wrapper as a task, or configure a local Gradle distribution.

1. Select ![the Gradle settings icon](https://www.jetbrains.com/help/img/idea/2020.3/icons.general.settings.svg) in the **Gradle** tool window to quickly access the **Gradle** settings page.

   ![Gradle settings](https://www.jetbrains.com/help/img/idea/2020.3/gradle_settings_use_gradle_from.png)

   

2. In the

    

   Use Gradle from

    

   list select one of the following options:

   - 'gradle-wrapper.properties' file

     : this is a recommended default option that uses Gradle wrapper.

     In this case you delegate the update of Gradle versions to Gradle and get an automatic Gradle download for the build. This option also lets you build with a precise Gradle version. The Gradle version is saved in the **gradle-wrapper.properties** file in the **gradle** directory of your project and helps you eliminate any Gradle version problems.

   - 'wrapper' task in Gradle build script

     : select this option to configure a Gradle wrapper according to the

     ```
     wrapper
     ```

     task configuration. It might be convenient if you prefer to control which Gradle version to use in the project.

     If you used the default Gradle wrapper option and then switched to the Gradle `wrapper` task configuration, changes you made in the task automatically update during the project import.

   - **Specified location**: select this option if you want to manually download and use a specific Gradle version. Specify the location of your Gradle installation and JVM under which IntelliJ IDEA will run Gradle when you import the specified Gradle project and when you execute its tasks.

   Click OK to save the changes.

### Add VM options for the Gradle project﻿

You can specify VM options for your Gradle project using the **gradle.properties** file.

1. [Create](https://www.jetbrains.com/help/idea/gradle.html#gradle_create_project) or [open](https://www.jetbrains.com/help/idea/gradle.html#gradle_import_project_start) your Gradle project.

2. In the **Project** tool window, right-click the project and from the context menu, select **New | File**.

3. In the **New File** dialog, enter **gradle.properties** as a filename and click **OK**.

4. Open the created file in the editor and add the VM options you need.

   ![the gradle.properties file](https://www.jetbrains.com/help/img/idea/2020.3/gradle_vm_options.png)

   

For more information, refer to the [Gradle documentation](https://docs.gradle.org/current/userguide/build_environment.html#sec:gradle_configuration_properties).