# Project 9.4 from Goodrich's book
Required project for CS600 Advanced Algorithm Design and Implementation

## About
This is my solution for project 9.4 from Goodrich's book: a Java implementation of a search engine using trie for the pages of a small Web site. 
I implemented my own trie using HashMap. I used [this website](https://www.xml-sitemaps.com) to generate a .txt file with the pages of a website.
Also, I used [jsoup](https://jsoup.org) to work with HTML and extract the words from the pages. 
The stop words were obtained [here](http://xpo6.com/list-of-english-stop-words/).

I populate a trie with the words extracted from the pages and store their occurrency list. Then, the trie is ready to perform searching.

## Setup
[Here](https://github.com/llaryssa/CS600-project9.4/blob/master/jsoup-1.9.1.jar) is the .jar for jsoup. You need to import that to the project to work.

## Running
When running, the program will ask you a few things through the console. 
When the name of the website file, you have to choose one filename from [pages/](https://github.com/llaryssa/CS600-project9.4/tree/master/pages)
or generate your own for the program to read. Then the program will ask what words you want to search. When done, type *esc*.
