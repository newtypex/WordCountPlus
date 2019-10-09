# WordCountPlus

This is a GUI tool I wrote for a translation company.

**Function:** 

This tool allows the manager to count the number of non-punctuation characters in a batch of text files, which is used to calculate the payment owed to their corresponding translator(s).

**Features:**

- Enables ignoring characters between pairs of brackets and/or ignoring duplicate lines, etc.
- All settings are saved and loaded automatically
- Drag & Drop feature and folderwide searching makes it easy and straightforward to use

**Structure:**

- WordCount.java: the main class of the tool. Please see the annotations inside the code.
- FileDrop.java: this is a class for the drag and drop feature
- BackgroundPanel.java: this is a class needed for image visualization
- "resource" folder: contains the files neccessary for this tool Please move this folder in bin/wordCountPlus/.