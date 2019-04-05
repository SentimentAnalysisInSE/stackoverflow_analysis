# Sentiment Analysis in SE
## Clay Asato
## Dr. Bruno da Silva

# Overview
This project seeks to find a relationship between popular posts on StackOverflow and specific character traits of authors.
We will use the IBM Personality Insights API and SOTorrent databases to analyze post bodies.

# Preliminary Findings
The authors of the top ten posts in score of all time wer taken in one query then passed to another set of queries that took the top ten posts by score of each of theos authors. Resulting posts were concatenated and cleaned of HTML tags and source code and sent to the IBM Personality Insights API for processing.
After running a small sample size of nine authors, we found that the sample set had general trends of high Openness and Neuroticism and low agreebleness.
