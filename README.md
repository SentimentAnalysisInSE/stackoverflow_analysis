# Understanding the Personality Traits of Stack Overflow Users: Text Analysis with IBM Personality Insights
## Clay Asato
## Dr. Bruno da Silva

# Abstract
The human aspect of programming has become a popular area of study in the Software Engineering community in recent years. Industry professionals and researchers alike are becoming more aware of sentiment, emotion, and personality traits in software engineering by exploring advancements in text analysis tools The Big Five personality model has been considered as a reliable model for mapping human personality traits. IBM Cloud Watson services provides an implementation of the Big 5 model for analyzing personality facets on a body of text. Therefore, this project aims to analyze the posts of Stack Overflow users to find a link between successful users and certain personality traits. Posts from Stack Overflow Users are concatenated and sent to the IBM Personality Insights API. Results are programmatically aggregated. Statistical analysis is run on the results. Trends in both trial groups were discovered. Both displayed high Openness and Neuroticism as well as low Agreeableness.

## Paper
The final report for the project is available [here](https://github.com/SentimentAnalysisInSE/stackoverflow_analysis/blob/master/PersonalityAnalysisStackOverflow.pdf).

## Directories in Repo
Here's a brief description of some of the subdirectories in the Repo:

| **Directory Name** | **Description** |
| ---- | ---- |
| First Run | Initial trial run of 9 of the highest scoring post authors|
| Second Run | Second preliminary run with 40 of the highest scoring post authors|
| HighScoreResults_json | Around 100 of the top scoring posts' authors analyses. This data was used in the final report|
| ReputationResults_json | Around 100 of the highest Reputation users' analyses. This data was used in the final report|

## Additional Data
In addition to the Big Five personality facets provided, IBM Personality Insights also returns percentile scores for some sub-facets of each of the Big Five as well as a set of scores for needs and values. The additional information is available in the raw JSON format returned by Personality Insights or can be viewed [here\(Google Docs\)](https://docs.google.com/spreadsheets/d/10UJuorLYhImkMU2a1kVQ7BTFAlEhHZviHdPdcPee-zE/edit?usp=sharing).

## Cal Poly Industry Advisory Board Poster
A poster with the project overview and results was also created. A digital version of the poster can be viewed [here \(PDF\)](https://github.com/SentimentAnalysisInSE/stackoverflow_analysis/blob/master/IABPoster.pdf). Additional visual aides used are available [here \(Google Slides\)](https://docs.google.com/presentation/d/e/2PACX-1vRf5c0TPlDm3TFKV2SxkXHReRq9AZL5GWhHy6AO7ez5Q7webBuMpqL9TopBmL_GyljtHwj1wzhFpRbc/pub?start=false&loop=false&delayms=10000)

#### Credits
This project utilizes the SOTorrent dataset, which allows for SQL access on the Stack Overflow late 2018 Datadump. [More info about SOTorrent](https://empirical-software.engineering/projects/sotorrent/#)

[IBM Cloud Personality Insights](https://www.ibm.com/watson/services/personality-insights/): IBM provides a [Personality Insights Java API](https://github.com/watson-developer-cloud/java-sdk) which was used for this project. As of 2019, IBM provides a set of free requests to users who want to test Personality Insights and other services.
