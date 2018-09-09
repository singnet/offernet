--- 
title: "Yet some more experiment of decentralized vs centralized search"
author: "Kabir Veitas (kabir@singularitynet.io)"
date: "2018-09-09"
bibliography: book.bib
documentclass: book
github-repo: singnet/offernet
link-citations: yes
site: bookdown::bookdown_site
biblio-style: apalike
url: http\://seankross.com/bookdown-start/
---




# Introduction

The goal was to procude data that would allow to compare the centralized and decentralized algorithms performance when the number of item nodes or number of similarity nodes increases. The problem is that number of nodes and links in the graph are obviously related -- so they are not independent variables. Furthermore, number of links is not a direct parameter of the simulation. Rather `similarity` links get created during a simulation and depend on `similarityConnectThreshold` parameters which regulates how similar item values should be in order to be connected by an explicit link, and the item values are initialized randomly. In short, number of links is an emergent variable, therefore the following run was meant to try out several parameter values (based on the knowledge from previous experiments) in order to come up with graphs and link numbers that are comparable.



|No. | expermentId | Comment |
|:--|:--|:--|
|1 | EXP09-05-02-15-an9jYp | agentNumbers=[400, 500, 600] : chainLengths=[10] : randomWorksNumberMultipliers=[1] : maxDistances=[10] : similaritySearchThresholds=[1] : similarityConnectThresholds=[0.9, 0.93, 0.96, 0.99] : message=[pre-generated smallWorld graph of agents with known diameters < 10] |
|2 | EXP09-05-11-49-gcWcHs | Same as above, only agentNumbers=[700, 800] |
|3 | EXP09-07-02-54-hATxwM | Same as above, agentNumbers=[700, 800], randomWorksNumberMultipliers=[2] |

<!--#### variables: -->
   

# Experiment  1 

agentNumbers=[400, 500, 600] : chainLengths=[10] : randomWorksNumberMultipliers=[1] : maxDistances=[10] : similaritySearchThresholds=[1] : similarityConnectThresholds=[0.9, 0.93, 0.96, 0.99] : message=[pre-generated smallWorld graph of agents with known diameters < 10] 

## Experimental set up 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;">   </th>
   <th style="text-align:left;"> Values </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> experimentId </td>
   <td style="text-align:left;"> EXP09-05-02-15-an9jYp </td>
  </tr>
  <tr>
   <td style="text-align:left;"> agentNumbers </td>
   <td style="text-align:left;"> 400, 500, 600 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> maxDistances </td>
   <td style="text-align:left;"> 10 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> randomWorksNumberMultipliers </td>
   <td style="text-align:left;"> 1 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> similarityConnectThresholds </td>
   <td style="text-align:left;"> 0.9, 0.93, 0.96, 0.99 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> chainLengths </td>
   <td style="text-align:left;"> 10 </td>
  </tr>
</tbody>
</table>
## Descriptive analysis of each simulation

###  SIM09-05-02-15-GbI7At--CV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 400 </td>
   <td style="text-align:left;width: 15em; "> 0.9 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 410 </td>
   <td style="text-align:right;width: 5em; "> 131,000 </td>
   <td style="text-align:right;width: 5em; "> 410 </td>
   <td style="text-align:right;width: 5em; "> 410 </td>
   <td style="text-align:right;width: 5em; "> 2,401 </td>
   <td style="text-align:right;width: 5em; "> 134,631 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 401 </td>
   <td style="text-align:right;width: 5em; "> 820 </td>
   <td style="text-align:right;width: 5em; "> 410 </td>
   <td style="text-align:right;width: 5em; "> 1,631 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-1.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-2.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-3.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> connectIfSimilar </td>
   <td style="text-align:right;"> 335,790 </td>
   <td style="text-align:right;"> 1,666,350 </td>
   <td style="text-align:left;"> 4.9624765478424 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> existsSimilarity </td>
   <td style="text-align:right;"> 335,790 </td>
   <td style="text-align:right;"> 1,657,838 </td>
   <td style="text-align:left;"> 4.93712737127371 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> similarityEdges </td>
   <td style="text-align:right;"> 335,790 </td>
   <td style="text-align:right;"> 1,622,995 </td>
   <td style="text-align:left;"> 4.83336311385092 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connectAllSimilar </td>
   <td style="text-align:right;"> 820 </td>
   <td style="text-align:right;"> 1,670,022 </td>
   <td style="text-align:left;"> 2036.61219512195 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 403 </td>
   <td style="text-align:right;"> 19,512 </td>
   <td style="text-align:left;"> 48.4168734491315 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connect </td>
   <td style="text-align:right;"> 219 </td>
   <td style="text-align:right;"> 575 </td>
   <td style="text-align:left;"> 2.62557077625571 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 1,008,812 </td>
   <td style="text-align:right;font-weight: bold;"> 6,637,292 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  1 

<button class="btn btn-primary" data-toggle="collapse" data-target="#VIGKMUIQCW"> Show/Hide foundCycle query </button> 
 <div id="VIGKMUIQCW" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-02-15-GbI7At--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  0 

<button class="btn btn-primary" data-toggle="collapse" data-target="#MDAGMWBQMP"> Show/Hide foundPath query </button> 
 <div id="MDAGMWBQMP" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-02-15-GbI7At--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-05-02-15-GbI7At--DV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 400 </td>
   <td style="text-align:left;width: 15em; "> 0.9 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 410 </td>
   <td style="text-align:right;width: 5em; "> 130,561 </td>
   <td style="text-align:right;width: 5em; "> 410 </td>
   <td style="text-align:right;width: 5em; "> 410 </td>
   <td style="text-align:right;width: 5em; "> 2,401 </td>
   <td style="text-align:right;width: 5em; "> 134,192 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 401 </td>
   <td style="text-align:right;width: 5em; "> 820 </td>
   <td style="text-align:right;width: 5em; "> 410 </td>
   <td style="text-align:right;width: 5em; "> 1,631 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-4.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-5.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-6.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;">   </th>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> 1 </td>
   <td style="text-align:left;"> knowsAgent </td>
   <td style="text-align:right;"> 2,401 </td>
   <td style="text-align:right;"> 5,587 </td>
   <td style="text-align:left;"> 2.32694710537276 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> 2 </td>
   <td style="text-align:left;"> addItemToWork </td>
   <td style="text-align:right;"> 820 </td>
   <td style="text-align:right;"> 12,675 </td>
   <td style="text-align:left;"> 15.4573170731707 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> 3 </td>
   <td style="text-align:left;"> ownsWork </td>
   <td style="text-align:right;"> 410 </td>
   <td style="text-align:right;"> 6,619 </td>
   <td style="text-align:left;"> 16.1439024390244 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> 4 </td>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 403 </td>
   <td style="text-align:right;"> 21,094 </td>
   <td style="text-align:left;"> 52.3424317617866 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> 5 </td>
   <td style="text-align:left;"> searchAndConnect </td>
   <td style="text-align:right;"> 401 </td>
   <td style="text-align:right;"> 1,328,368 </td>
   <td style="text-align:left;"> 3312.63840399002 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> 6 </td>
   <td style="text-align:left;"> cycleSearch </td>
   <td style="text-align:right;"> 37 </td>
   <td style="text-align:right;"> 6,590 </td>
   <td style="text-align:left;"> 178.108108108108 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> 7 </td>
   <td style="text-align:left;"> getWorks </td>
   <td style="text-align:right;"> 37 </td>
   <td style="text-align:right;"> 2,853 </td>
   <td style="text-align:left;"> 77.1081081081081 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> 8 </td>
   <td style="text-align:left;"> individualCycleSearch </td>
   <td style="text-align:right;"> 37 </td>
   <td style="text-align:right;"> 5 </td>
   <td style="text-align:left;"> 0.135135135135135 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:left;"> getWorksItems </td>
   <td style="text-align:right;"> 18 </td>
   <td style="text-align:right;"> 60 </td>
   <td style="text-align:left;"> 3.33333333333333 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> 11 </td>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 4,564 </td>
   <td style="text-align:right;font-weight: bold;"> 1,383,851 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  18 

<button class="btn btn-primary" data-toggle="collapse" data-target="#PBBQGNZHMZ"> Show/Hide foundCycle query </button> 
 <div id="PBBQGNZHMZ" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-02-15-GbI7At--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  0 

<button class="btn btn-primary" data-toggle="collapse" data-target="#QDAZKSLTEU"> Show/Hide foundPath query </button> 
 <div id="QDAZKSLTEU" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-02-15-GbI7At--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-05-02-44-2F8BLS--CV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 400 </td>
   <td style="text-align:left;width: 15em; "> 0.93 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 410 </td>
   <td style="text-align:right;width: 5em; "> 94,248 </td>
   <td style="text-align:right;width: 5em; "> 410 </td>
   <td style="text-align:right;width: 5em; "> 410 </td>
   <td style="text-align:right;width: 5em; "> 2,401 </td>
   <td style="text-align:right;width: 5em; "> 97,879 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 401 </td>
   <td style="text-align:right;width: 5em; "> 820 </td>
   <td style="text-align:right;width: 5em; "> 410 </td>
   <td style="text-align:right;width: 5em; "> 1,631 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-7.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-8.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-9.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> connectIfSimilar </td>
   <td style="text-align:right;"> 335,790 </td>
   <td style="text-align:right;"> 1,318,300 </td>
   <td style="text-align:left;"> 3.92596563328271 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> existsSimilarity </td>
   <td style="text-align:right;"> 335,790 </td>
   <td style="text-align:right;"> 1,310,297 </td>
   <td style="text-align:left;"> 3.90213228505911 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> similarityEdges </td>
   <td style="text-align:right;"> 335,790 </td>
   <td style="text-align:right;"> 1,282,681 </td>
   <td style="text-align:left;"> 3.81989040769529 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connectAllSimilar </td>
   <td style="text-align:right;"> 820 </td>
   <td style="text-align:right;"> 1,321,957 </td>
   <td style="text-align:left;"> 1612.14268292683 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 403 </td>
   <td style="text-align:right;"> 19,191 </td>
   <td style="text-align:left;"> 47.6203473945409 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connect </td>
   <td style="text-align:right;"> 149 </td>
   <td style="text-align:right;"> 314 </td>
   <td style="text-align:left;"> 2.10738255033557 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 1,008,742 </td>
   <td style="text-align:right;font-weight: bold;"> 5,252,740 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  1 

<button class="btn btn-primary" data-toggle="collapse" data-target="#QMXPGJFLRJ"> Show/Hide foundCycle query </button> 
 <div id="QMXPGJFLRJ" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-02-44-2F8BLS--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  0 

<button class="btn btn-primary" data-toggle="collapse" data-target="#BVZWINPUXN"> Show/Hide foundPath query </button> 
 <div id="BVZWINPUXN" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-02-44-2F8BLS--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-05-02-44-2F8BLS--DV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 400 </td>
   <td style="text-align:left;width: 15em; "> 0.93 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 410 </td>
   <td style="text-align:right;width: 5em; "> 93,950 </td>
   <td style="text-align:right;width: 5em; "> 410 </td>
   <td style="text-align:right;width: 5em; "> 410 </td>
   <td style="text-align:right;width: 5em; "> 2,401 </td>
   <td style="text-align:right;width: 5em; "> 97,581 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 401 </td>
   <td style="text-align:right;width: 5em; "> 820 </td>
   <td style="text-align:right;width: 5em; "> 410 </td>
   <td style="text-align:right;width: 5em; "> 1,631 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-10.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-11.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-12.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;">   </th>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> 1 </td>
   <td style="text-align:left;"> knowsAgent </td>
   <td style="text-align:right;"> 2,401 </td>
   <td style="text-align:right;"> 4,357 </td>
   <td style="text-align:left;"> 1.81466055810079 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> 2 </td>
   <td style="text-align:left;"> addItemToWork </td>
   <td style="text-align:right;"> 820 </td>
   <td style="text-align:right;"> 7,066 </td>
   <td style="text-align:left;"> 8.61707317073171 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> 3 </td>
   <td style="text-align:left;"> ownsWork </td>
   <td style="text-align:right;"> 410 </td>
   <td style="text-align:right;"> 3,913 </td>
   <td style="text-align:left;"> 9.54390243902439 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> 4 </td>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 403 </td>
   <td style="text-align:right;"> 19,596 </td>
   <td style="text-align:left;"> 48.6253101736973 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> 5 </td>
   <td style="text-align:left;"> searchAndConnect </td>
   <td style="text-align:right;"> 401 </td>
   <td style="text-align:right;"> 1,095,425 </td>
   <td style="text-align:left;"> 2731.73316708229 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> 6 </td>
   <td style="text-align:left;"> cycleSearch </td>
   <td style="text-align:right;"> 30 </td>
   <td style="text-align:right;"> 3,939 </td>
   <td style="text-align:left;"> 131.3 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> 7 </td>
   <td style="text-align:left;"> getWorks </td>
   <td style="text-align:right;"> 30 </td>
   <td style="text-align:right;"> 4,469 </td>
   <td style="text-align:left;"> 148.966666666667 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> 8 </td>
   <td style="text-align:left;"> individualCycleSearch </td>
   <td style="text-align:right;"> 30 </td>
   <td style="text-align:right;"> 0 </td>
   <td style="text-align:left;"> 0 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:left;"> getWorksItems </td>
   <td style="text-align:right;"> 18 </td>
   <td style="text-align:right;"> 78 </td>
   <td style="text-align:left;"> 4.33333333333333 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> 11 </td>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 4,543 </td>
   <td style="text-align:right;font-weight: bold;"> 1,138,843 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  18 

<button class="btn btn-primary" data-toggle="collapse" data-target="#DXCUQKZFBQ"> Show/Hide foundCycle query </button> 
 <div id="DXCUQKZFBQ" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-02-44-2F8BLS--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  0 

<button class="btn btn-primary" data-toggle="collapse" data-target="#GSUTOQLHIV"> Show/Hide foundPath query </button> 
 <div id="GSUTOQLHIV" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-02-44-2F8BLS--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-05-03-07-a6ciiw--CV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 400 </td>
   <td style="text-align:left;width: 15em; "> 0.96 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 410 </td>
   <td style="text-align:right;width: 5em; "> 54,712 </td>
   <td style="text-align:right;width: 5em; "> 410 </td>
   <td style="text-align:right;width: 5em; "> 410 </td>
   <td style="text-align:right;width: 5em; "> 2,401 </td>
   <td style="text-align:right;width: 5em; "> 58,343 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 401 </td>
   <td style="text-align:right;width: 5em; "> 820 </td>
   <td style="text-align:right;width: 5em; "> 410 </td>
   <td style="text-align:right;width: 5em; "> 1,631 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-13.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-14.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-15.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;">   </th>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> 1 </td>
   <td style="text-align:left;"> connectIfSimilar </td>
   <td style="text-align:right;"> 335,790 </td>
   <td style="text-align:right;"> 967,024 </td>
   <td style="text-align:left;"> 2.87984752374996 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> 2 </td>
   <td style="text-align:left;"> existsSimilarity </td>
   <td style="text-align:right;"> 335,790 </td>
   <td style="text-align:right;"> 959,985 </td>
   <td style="text-align:left;"> 2.8588850174216 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> 3 </td>
   <td style="text-align:left;"> similarityEdges </td>
   <td style="text-align:right;"> 335,790 </td>
   <td style="text-align:right;"> 939,663 </td>
   <td style="text-align:left;"> 2.79836504958456 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> 4 </td>
   <td style="text-align:left;"> connectAllSimilar </td>
   <td style="text-align:right;"> 820 </td>
   <td style="text-align:right;"> 970,525 </td>
   <td style="text-align:left;"> 1183.56707317073 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> 5 </td>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 403 </td>
   <td style="text-align:right;"> 19,663 </td>
   <td style="text-align:left;"> 48.7915632754342 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> 6 </td>
   <td style="text-align:left;"> connect </td>
   <td style="text-align:right;"> 110 </td>
   <td style="text-align:right;"> 304 </td>
   <td style="text-align:left;"> 2.76363636363636 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> 8 </td>
   <td style="text-align:left;"> searchAndConnect </td>
   <td style="text-align:right;"> 3 </td>
   <td style="text-align:right;"> 6,266 </td>
   <td style="text-align:left;"> 2088.66666666667 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> 11 </td>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 1,008,706 </td>
   <td style="text-align:right;font-weight: bold;"> 3,863,430 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  1 

<button class="btn btn-primary" data-toggle="collapse" data-target="#LMGOJVRTWR"> Show/Hide foundCycle query </button> 
 <div id="LMGOJVRTWR" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-03-07-a6ciiw--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  0 

<button class="btn btn-primary" data-toggle="collapse" data-target="#RXGBKLIIZG"> Show/Hide foundPath query </button> 
 <div id="RXGBKLIIZG" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-03-07-a6ciiw--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-05-03-07-a6ciiw--DV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 400 </td>
   <td style="text-align:left;width: 15em; "> 0.96 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 410 </td>
   <td style="text-align:right;width: 5em; "> 54,484 </td>
   <td style="text-align:right;width: 5em; "> 410 </td>
   <td style="text-align:right;width: 5em; "> 410 </td>
   <td style="text-align:right;width: 5em; "> 2,401 </td>
   <td style="text-align:right;width: 5em; "> 58,115 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 401 </td>
   <td style="text-align:right;width: 5em; "> 820 </td>
   <td style="text-align:right;width: 5em; "> 410 </td>
   <td style="text-align:right;width: 5em; "> 1,631 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-16.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-17.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-18.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> knowsAgent </td>
   <td style="text-align:right;"> 2,401 </td>
   <td style="text-align:right;"> 4,050 </td>
   <td style="text-align:left;"> 1.68679716784673 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> addItemToWork </td>
   <td style="text-align:right;"> 820 </td>
   <td style="text-align:right;"> 8,648 </td>
   <td style="text-align:left;"> 10.5463414634146 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> ownsWork </td>
   <td style="text-align:right;"> 410 </td>
   <td style="text-align:right;"> 3,791 </td>
   <td style="text-align:left;"> 9.24634146341463 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 403 </td>
   <td style="text-align:right;"> 19,736 </td>
   <td style="text-align:left;"> 48.9727047146402 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> searchAndConnect </td>
   <td style="text-align:right;"> 398 </td>
   <td style="text-align:right;"> 810,112 </td>
   <td style="text-align:left;"> 2035.45728643216 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorksItems </td>
   <td style="text-align:right;"> 18 </td>
   <td style="text-align:right;"> 33 </td>
   <td style="text-align:left;"> 1.83333333333333 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> cycleSearch </td>
   <td style="text-align:right;"> 15 </td>
   <td style="text-align:right;"> 6,842 </td>
   <td style="text-align:left;"> 456.133333333333 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorks </td>
   <td style="text-align:right;"> 15 </td>
   <td style="text-align:right;"> 4,201 </td>
   <td style="text-align:left;"> 280.066666666667 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> individualCycleSearch </td>
   <td style="text-align:right;"> 15 </td>
   <td style="text-align:right;"> 0 </td>
   <td style="text-align:left;"> 0 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 4,495 </td>
   <td style="text-align:right;font-weight: bold;"> 857,413 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  3 

<button class="btn btn-primary" data-toggle="collapse" data-target="#XPHVQCLGQP"> Show/Hide foundCycle query </button> 
 <div id="XPHVQCLGQP" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-03-07-a6ciiw--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  0 

<button class="btn btn-primary" data-toggle="collapse" data-target="#RLNPHWFJWK"> Show/Hide foundPath query </button> 
 <div id="RLNPHWFJWK" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-03-07-a6ciiw--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-05-03-25-dk7PZu--CV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 400 </td>
   <td style="text-align:left;width: 15em; "> 0.99 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 410 </td>
   <td style="text-align:right;width: 5em; "> 13,554 </td>
   <td style="text-align:right;width: 5em; "> 410 </td>
   <td style="text-align:right;width: 5em; "> 410 </td>
   <td style="text-align:right;width: 5em; "> 2,401 </td>
   <td style="text-align:right;width: 5em; "> 17,185 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 401 </td>
   <td style="text-align:right;width: 5em; "> 820 </td>
   <td style="text-align:right;width: 5em; "> 410 </td>
   <td style="text-align:right;width: 5em; "> 1,631 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-19.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-20.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-21.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> connectIfSimilar </td>
   <td style="text-align:right;"> 335,790 </td>
   <td style="text-align:right;"> 565,899 </td>
   <td style="text-align:left;"> 1.68527651210578 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> existsSimilarity </td>
   <td style="text-align:right;"> 335,790 </td>
   <td style="text-align:right;"> 560,331 </td>
   <td style="text-align:left;"> 1.66869471991423 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> similarityEdges </td>
   <td style="text-align:right;"> 335,790 </td>
   <td style="text-align:right;"> 549,636 </td>
   <td style="text-align:left;"> 1.63684445635665 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connectAllSimilar </td>
   <td style="text-align:right;"> 820 </td>
   <td style="text-align:right;"> 568,936 </td>
   <td style="text-align:left;"> 693.824390243902 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 403 </td>
   <td style="text-align:right;"> 20,229 </td>
   <td style="text-align:left;"> 50.1960297766749 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connect </td>
   <td style="text-align:right;"> 24 </td>
   <td style="text-align:right;"> 37 </td>
   <td style="text-align:left;"> 1.54166666666667 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 1,008,617 </td>
   <td style="text-align:right;font-weight: bold;"> 2,265,068 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  1 

<button class="btn btn-primary" data-toggle="collapse" data-target="#AORWMRLPZH"> Show/Hide foundCycle query </button> 
 <div id="AORWMRLPZH" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-03-25-dk7PZu--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  0 

<button class="btn btn-primary" data-toggle="collapse" data-target="#HDSAUANXSG"> Show/Hide foundPath query </button> 
 <div id="HDSAUANXSG" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-03-25-dk7PZu--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-05-03-25-dk7PZu--DV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 400 </td>
   <td style="text-align:left;width: 15em; "> 0.99 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 410 </td>
   <td style="text-align:right;width: 5em; "> 13,506 </td>
   <td style="text-align:right;width: 5em; "> 410 </td>
   <td style="text-align:right;width: 5em; "> 410 </td>
   <td style="text-align:right;width: 5em; "> 2,401 </td>
   <td style="text-align:right;width: 5em; "> 17,137 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 401 </td>
   <td style="text-align:right;width: 5em; "> 820 </td>
   <td style="text-align:right;width: 5em; "> 410 </td>
   <td style="text-align:right;width: 5em; "> 1,631 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-22.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-23.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-24.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> knowsAgent </td>
   <td style="text-align:right;"> 2,401 </td>
   <td style="text-align:right;"> 4,363 </td>
   <td style="text-align:left;"> 1.81715951686797 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> addItemToWork </td>
   <td style="text-align:right;"> 820 </td>
   <td style="text-align:right;"> 5,752 </td>
   <td style="text-align:left;"> 7.01463414634146 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> ownsWork </td>
   <td style="text-align:right;"> 410 </td>
   <td style="text-align:right;"> 2,912 </td>
   <td style="text-align:left;"> 7.10243902439024 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 403 </td>
   <td style="text-align:right;"> 20,514 </td>
   <td style="text-align:left;"> 50.9032258064516 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> searchAndConnect </td>
   <td style="text-align:right;"> 401 </td>
   <td style="text-align:right;"> 663,613 </td>
   <td style="text-align:left;"> 1654.89526184539 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> cycleSearch </td>
   <td style="text-align:right;"> 20 </td>
   <td style="text-align:right;"> 5,307 </td>
   <td style="text-align:left;"> 265.35 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorks </td>
   <td style="text-align:right;"> 20 </td>
   <td style="text-align:right;"> 1,979 </td>
   <td style="text-align:left;"> 98.95 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> individualCycleSearch </td>
   <td style="text-align:right;"> 20 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 0.05 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorksItems </td>
   <td style="text-align:right;"> 18 </td>
   <td style="text-align:right;"> 34 </td>
   <td style="text-align:left;"> 1.88888888888889 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 4,513 </td>
   <td style="text-align:right;font-weight: bold;"> 704,475 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  12 

<button class="btn btn-primary" data-toggle="collapse" data-target="#WOUCMLMDHS"> Show/Hide foundCycle query </button> 
 <div id="WOUCMLMDHS" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-03-25-dk7PZu--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  2 

<button class="btn btn-primary" data-toggle="collapse" data-target="#MHQSDCCJXS"> Show/Hide foundPath query </button> 
 <div id="MHQSDCCJXS" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-03-25-dk7PZu--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-05-03-35-JEKiMx--CV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 500 </td>
   <td style="text-align:left;width: 15em; "> 0.9 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 510 </td>
   <td style="text-align:right;width: 5em; "> 203,218 </td>
   <td style="text-align:right;width: 5em; "> 510 </td>
   <td style="text-align:right;width: 5em; "> 510 </td>
   <td style="text-align:right;width: 5em; "> 3,001 </td>
   <td style="text-align:right;width: 5em; "> 207,749 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 501 </td>
   <td style="text-align:right;width: 5em; "> 1,020 </td>
   <td style="text-align:right;width: 5em; "> 510 </td>
   <td style="text-align:right;width: 5em; "> 2,031 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-25.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-26.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-27.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> connectIfSimilar </td>
   <td style="text-align:right;"> 519,690 </td>
   <td style="text-align:right;"> 2,981,312 </td>
   <td style="text-align:left;"> 5.73671227077681 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> existsSimilarity </td>
   <td style="text-align:right;"> 519,690 </td>
   <td style="text-align:right;"> 2,967,443 </td>
   <td style="text-align:left;"> 5.71002520733514 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> similarityEdges </td>
   <td style="text-align:right;"> 519,690 </td>
   <td style="text-align:right;"> 2,898,060 </td>
   <td style="text-align:left;"> 5.57651676961265 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connectAllSimilar </td>
   <td style="text-align:right;"> 1,020 </td>
   <td style="text-align:right;"> 2,987,621 </td>
   <td style="text-align:left;"> 2929.04019607843 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 503 </td>
   <td style="text-align:right;"> 23,964 </td>
   <td style="text-align:left;"> 47.6421471172962 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connect </td>
   <td style="text-align:right;"> 292 </td>
   <td style="text-align:right;"> 905 </td>
   <td style="text-align:left;"> 3.09931506849315 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 1,560,885 </td>
   <td style="text-align:right;font-weight: bold;"> 11,859,305 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  1 

<button class="btn btn-primary" data-toggle="collapse" data-target="#QRZEFFSNQZ"> Show/Hide foundCycle query </button> 
 <div id="QRZEFFSNQZ" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-03-35-JEKiMx--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  0 

<button class="btn btn-primary" data-toggle="collapse" data-target="#KGDQNFQBHO"> Show/Hide foundPath query </button> 
 <div id="KGDQNFQBHO" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-03-35-JEKiMx--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-05-03-35-JEKiMx--DV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 500 </td>
   <td style="text-align:left;width: 15em; "> 0.9 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 510 </td>
   <td style="text-align:right;width: 5em; "> 202,595 </td>
   <td style="text-align:right;width: 5em; "> 510 </td>
   <td style="text-align:right;width: 5em; "> 510 </td>
   <td style="text-align:right;width: 5em; "> 3,001 </td>
   <td style="text-align:right;width: 5em; "> 207,126 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 501 </td>
   <td style="text-align:right;width: 5em; "> 1,020 </td>
   <td style="text-align:right;width: 5em; "> 510 </td>
   <td style="text-align:right;width: 5em; "> 2,031 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-28.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-29.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-30.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> knowsAgent </td>
   <td style="text-align:right;"> 3,001 </td>
   <td style="text-align:right;"> 5,470 </td>
   <td style="text-align:left;"> 1.82272575808064 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> addItemToWork </td>
   <td style="text-align:right;"> 1,020 </td>
   <td style="text-align:right;"> 6,612 </td>
   <td style="text-align:left;"> 6.48235294117647 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> ownsWork </td>
   <td style="text-align:right;"> 510 </td>
   <td style="text-align:right;"> 3,340 </td>
   <td style="text-align:left;"> 6.54901960784314 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 503 </td>
   <td style="text-align:right;"> 24,090 </td>
   <td style="text-align:left;"> 47.8926441351889 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> searchAndConnect </td>
   <td style="text-align:right;"> 501 </td>
   <td style="text-align:right;"> 2,117,107 </td>
   <td style="text-align:left;"> 4225.7624750499 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> cycleSearch </td>
   <td style="text-align:right;"> 71 </td>
   <td style="text-align:right;"> 27,670 </td>
   <td style="text-align:left;"> 389.718309859155 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorks </td>
   <td style="text-align:right;"> 71 </td>
   <td style="text-align:right;"> 10,862 </td>
   <td style="text-align:left;"> 152.985915492958 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> individualCycleSearch </td>
   <td style="text-align:right;"> 71 </td>
   <td style="text-align:right;"> 4 </td>
   <td style="text-align:left;"> 0.0563380281690141 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 5,748 </td>
   <td style="text-align:right;font-weight: bold;"> 2,195,155 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  33 

<button class="btn btn-primary" data-toggle="collapse" data-target="#VVHUVYHTKW"> Show/Hide foundCycle query </button> 
 <div id="VVHUVYHTKW" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-03-35-JEKiMx--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  6 

<button class="btn btn-primary" data-toggle="collapse" data-target="#OOCEQPYJMU"> Show/Hide foundPath query </button> 
 <div id="OOCEQPYJMU" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-03-35-JEKiMx--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-05-04-27-kUGfQG--CV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 500 </td>
   <td style="text-align:left;width: 15em; "> 0.93 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 510 </td>
   <td style="text-align:right;width: 5em; "> 145,916 </td>
   <td style="text-align:right;width: 5em; "> 510 </td>
   <td style="text-align:right;width: 5em; "> 510 </td>
   <td style="text-align:right;width: 5em; "> 3,001 </td>
   <td style="text-align:right;width: 5em; "> 150,447 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 501 </td>
   <td style="text-align:right;width: 5em; "> 1,020 </td>
   <td style="text-align:right;width: 5em; "> 510 </td>
   <td style="text-align:right;width: 5em; "> 2,031 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-31.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-32.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-33.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> connectIfSimilar </td>
   <td style="text-align:right;"> 519,690 </td>
   <td style="text-align:right;"> 2,392,965 </td>
   <td style="text-align:left;"> 4.60460081971945 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> existsSimilarity </td>
   <td style="text-align:right;"> 519,690 </td>
   <td style="text-align:right;"> 2,380,263 </td>
   <td style="text-align:left;"> 4.58015932575189 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> similarityEdges </td>
   <td style="text-align:right;"> 519,690 </td>
   <td style="text-align:right;"> 2,327,000 </td>
   <td style="text-align:left;"> 4.47766937982259 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connectAllSimilar </td>
   <td style="text-align:right;"> 1,020 </td>
   <td style="text-align:right;"> 2,398,980 </td>
   <td style="text-align:left;"> 2351.94117647059 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 503 </td>
   <td style="text-align:right;"> 24,253 </td>
   <td style="text-align:left;"> 48.2166998011928 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connect </td>
   <td style="text-align:right;"> 200 </td>
   <td style="text-align:right;"> 570 </td>
   <td style="text-align:left;"> 2.85 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 1,560,793 </td>
   <td style="text-align:right;font-weight: bold;"> 9,524,031 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  1 

<button class="btn btn-primary" data-toggle="collapse" data-target="#TCVMZIBHYN"> Show/Hide foundCycle query </button> 
 <div id="TCVMZIBHYN" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-04-27-kUGfQG--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  0 

<button class="btn btn-primary" data-toggle="collapse" data-target="#FNHIJJGLYZ"> Show/Hide foundPath query </button> 
 <div id="FNHIJJGLYZ" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-04-27-kUGfQG--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-05-04-27-kUGfQG--DV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 500 </td>
   <td style="text-align:left;width: 15em; "> 0.93 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 510 </td>
   <td style="text-align:right;width: 5em; "> 145,516 </td>
   <td style="text-align:right;width: 5em; "> 510 </td>
   <td style="text-align:right;width: 5em; "> 510 </td>
   <td style="text-align:right;width: 5em; "> 3,001 </td>
   <td style="text-align:right;width: 5em; "> 150,047 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 501 </td>
   <td style="text-align:right;width: 5em; "> 1,020 </td>
   <td style="text-align:right;width: 5em; "> 510 </td>
   <td style="text-align:right;width: 5em; "> 2,031 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-34.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-35.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-36.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> knowsAgent </td>
   <td style="text-align:right;"> 3,001 </td>
   <td style="text-align:right;"> 5,030 </td>
   <td style="text-align:left;"> 1.676107964012 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> addItemToWork </td>
   <td style="text-align:right;"> 1,020 </td>
   <td style="text-align:right;"> 11,663 </td>
   <td style="text-align:left;"> 11.4343137254902 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> ownsWork </td>
   <td style="text-align:right;"> 510 </td>
   <td style="text-align:right;"> 5,451 </td>
   <td style="text-align:left;"> 10.6882352941176 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 503 </td>
   <td style="text-align:right;"> 24,258 </td>
   <td style="text-align:left;"> 48.2266401590457 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> searchAndConnect </td>
   <td style="text-align:right;"> 501 </td>
   <td style="text-align:right;"> 1,873,300 </td>
   <td style="text-align:left;"> 3739.12175648703 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> cycleSearch </td>
   <td style="text-align:right;"> 54 </td>
   <td style="text-align:right;"> 1,789 </td>
   <td style="text-align:left;"> 33.1296296296296 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorks </td>
   <td style="text-align:right;"> 54 </td>
   <td style="text-align:right;"> 3,091 </td>
   <td style="text-align:left;"> 57.2407407407407 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> individualCycleSearch </td>
   <td style="text-align:right;"> 54 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 0.0185185185185185 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 5,697 </td>
   <td style="text-align:right;font-weight: bold;"> 1,924,583 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  30 

<button class="btn btn-primary" data-toggle="collapse" data-target="#LRHJNMVQRR"> Show/Hide foundCycle query </button> 
 <div id="LRHJNMVQRR" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-04-27-kUGfQG--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  0 

<button class="btn btn-primary" data-toggle="collapse" data-target="#RJHJFOFPYZ"> Show/Hide foundPath query </button> 
 <div id="RJHJFOFPYZ" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-04-27-kUGfQG--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-05-05-09-yMqjcT--CV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 500 </td>
   <td style="text-align:left;width: 15em; "> 0.96 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 510 </td>
   <td style="text-align:right;width: 5em; "> 85,058 </td>
   <td style="text-align:right;width: 5em; "> 510 </td>
   <td style="text-align:right;width: 5em; "> 510 </td>
   <td style="text-align:right;width: 5em; "> 3,001 </td>
   <td style="text-align:right;width: 5em; "> 89,589 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 501 </td>
   <td style="text-align:right;width: 5em; "> 1,020 </td>
   <td style="text-align:right;width: 5em; "> 510 </td>
   <td style="text-align:right;width: 5em; "> 2,031 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-37.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-38.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-39.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> connectIfSimilar </td>
   <td style="text-align:right;"> 519,690 </td>
   <td style="text-align:right;"> 1,730,033 </td>
   <td style="text-align:left;"> 3.32897111739691 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> existsSimilarity </td>
   <td style="text-align:right;"> 519,690 </td>
   <td style="text-align:right;"> 1,718,581 </td>
   <td style="text-align:left;"> 3.30693490350016 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> similarityEdges </td>
   <td style="text-align:right;"> 519,690 </td>
   <td style="text-align:right;"> 1,683,770 </td>
   <td style="text-align:left;"> 3.23995073986415 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connectAllSimilar </td>
   <td style="text-align:right;"> 1,020 </td>
   <td style="text-align:right;"> 1,735,708 </td>
   <td style="text-align:left;"> 1701.67450980392 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 503 </td>
   <td style="text-align:right;"> 24,454 </td>
   <td style="text-align:left;"> 48.6163021868787 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connect </td>
   <td style="text-align:right;"> 126 </td>
   <td style="text-align:right;"> 310 </td>
   <td style="text-align:left;"> 2.46031746031746 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> cycleSearch </td>
   <td style="text-align:right;"> 19 </td>
   <td style="text-align:right;"> 534 </td>
   <td style="text-align:left;"> 28.1052631578947 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorks </td>
   <td style="text-align:right;"> 18 </td>
   <td style="text-align:right;"> 91 </td>
   <td style="text-align:left;"> 5.05555555555556 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> searchAndConnect </td>
   <td style="text-align:right;"> 9 </td>
   <td style="text-align:right;"> 23,525 </td>
   <td style="text-align:left;"> 2613.88888888889 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 1,560,765 </td>
   <td style="text-align:right;font-weight: bold;"> 6,917,006 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  1 

<button class="btn btn-primary" data-toggle="collapse" data-target="#WZTCLJBCDB"> Show/Hide foundCycle query </button> 
 <div id="WZTCLJBCDB" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-05-09-yMqjcT--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  0 

<button class="btn btn-primary" data-toggle="collapse" data-target="#XWIUKJSDGK"> Show/Hide foundPath query </button> 
 <div id="XWIUKJSDGK" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-05-09-yMqjcT--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-05-05-09-yMqjcT--DV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 500 </td>
   <td style="text-align:left;width: 15em; "> 0.96 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 510 </td>
   <td style="text-align:right;width: 5em; "> 84,806 </td>
   <td style="text-align:right;width: 5em; "> 510 </td>
   <td style="text-align:right;width: 5em; "> 510 </td>
   <td style="text-align:right;width: 5em; "> 3,001 </td>
   <td style="text-align:right;width: 5em; "> 89,337 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 501 </td>
   <td style="text-align:right;width: 5em; "> 1,020 </td>
   <td style="text-align:right;width: 5em; "> 510 </td>
   <td style="text-align:right;width: 5em; "> 2,031 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-40.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-41.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-42.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> knowsAgent </td>
   <td style="text-align:right;"> 3,001 </td>
   <td style="text-align:right;"> 5,230 </td>
   <td style="text-align:left;"> 1.74275241586138 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> addItemToWork </td>
   <td style="text-align:right;"> 1,020 </td>
   <td style="text-align:right;"> 10,010 </td>
   <td style="text-align:left;"> 9.81372549019608 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> ownsWork </td>
   <td style="text-align:right;"> 510 </td>
   <td style="text-align:right;"> 5,151 </td>
   <td style="text-align:left;"> 10.1 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 503 </td>
   <td style="text-align:right;"> 24,579 </td>
   <td style="text-align:left;"> 48.8648111332008 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> searchAndConnect </td>
   <td style="text-align:right;"> 492 </td>
   <td style="text-align:right;"> 1,276,072 </td>
   <td style="text-align:left;"> 2593.64227642276 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> individualCycleSearch </td>
   <td style="text-align:right;"> 29 </td>
   <td style="text-align:right;"> 0 </td>
   <td style="text-align:left;"> 0 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorksItems </td>
   <td style="text-align:right;"> 18 </td>
   <td style="text-align:right;"> 57 </td>
   <td style="text-align:left;"> 3.16666666666667 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorks </td>
   <td style="text-align:right;"> 12 </td>
   <td style="text-align:right;"> 4,098 </td>
   <td style="text-align:left;"> 341.5 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> cycleSearch </td>
   <td style="text-align:right;"> 11 </td>
   <td style="text-align:right;"> 9,182 </td>
   <td style="text-align:left;"> 834.727272727273 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 5,596 </td>
   <td style="text-align:right;font-weight: bold;"> 1,334,379 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  3 

<button class="btn btn-primary" data-toggle="collapse" data-target="#ZWGWTYVEBY"> Show/Hide foundCycle query </button> 
 <div id="ZWGWTYVEBY" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-05-09-yMqjcT--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  1 

<button class="btn btn-primary" data-toggle="collapse" data-target="#ZGLJAIFURX"> Show/Hide foundPath query </button> 
 <div id="ZGLJAIFURX" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-05-09-yMqjcT--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-05-05-39-kF9FXt--CV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 500 </td>
   <td style="text-align:left;width: 15em; "> 0.99 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 510 </td>
   <td style="text-align:right;width: 5em; "> 21,000 </td>
   <td style="text-align:right;width: 5em; "> 510 </td>
   <td style="text-align:right;width: 5em; "> 510 </td>
   <td style="text-align:right;width: 5em; "> 3,001 </td>
   <td style="text-align:right;width: 5em; "> 25,531 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 501 </td>
   <td style="text-align:right;width: 5em; "> 1,020 </td>
   <td style="text-align:right;width: 5em; "> 510 </td>
   <td style="text-align:right;width: 5em; "> 2,031 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-43.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-44.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-45.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> connectIfSimilar </td>
   <td style="text-align:right;"> 519,690 </td>
   <td style="text-align:right;"> 962,983 </td>
   <td style="text-align:left;"> 1.85299505474417 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> existsSimilarity </td>
   <td style="text-align:right;"> 519,690 </td>
   <td style="text-align:right;"> 953,987 </td>
   <td style="text-align:left;"> 1.83568473513056 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> similarityEdges </td>
   <td style="text-align:right;"> 519,690 </td>
   <td style="text-align:right;"> 936,650 </td>
   <td style="text-align:left;"> 1.80232446266043 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connectAllSimilar </td>
   <td style="text-align:right;"> 1,020 </td>
   <td style="text-align:right;"> 968,061 </td>
   <td style="text-align:left;"> 949.079411764706 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 503 </td>
   <td style="text-align:right;"> 24,464 </td>
   <td style="text-align:left;"> 48.6361829025845 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connect </td>
   <td style="text-align:right;"> 30 </td>
   <td style="text-align:right;"> 102 </td>
   <td style="text-align:left;"> 3.4 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 1,560,623 </td>
   <td style="text-align:right;font-weight: bold;"> 3,846,247 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  1 

<button class="btn btn-primary" data-toggle="collapse" data-target="#BENWECZDNR"> Show/Hide foundCycle query </button> 
 <div id="BENWECZDNR" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-05-39-kF9FXt--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  0 

<button class="btn btn-primary" data-toggle="collapse" data-target="#YYJEVLYNYE"> Show/Hide foundPath query </button> 
 <div id="YYJEVLYNYE" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-05-39-kF9FXt--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-05-05-39-kF9FXt--DV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 500 </td>
   <td style="text-align:left;width: 15em; "> 0.99 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 510 </td>
   <td style="text-align:right;width: 5em; "> 20,940 </td>
   <td style="text-align:right;width: 5em; "> 510 </td>
   <td style="text-align:right;width: 5em; "> 510 </td>
   <td style="text-align:right;width: 5em; "> 3,001 </td>
   <td style="text-align:right;width: 5em; "> 25,471 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 501 </td>
   <td style="text-align:right;width: 5em; "> 1,020 </td>
   <td style="text-align:right;width: 5em; "> 510 </td>
   <td style="text-align:right;width: 5em; "> 2,031 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-46.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-47.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-48.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> knowsAgent </td>
   <td style="text-align:right;"> 3,001 </td>
   <td style="text-align:right;"> 5,319 </td>
   <td style="text-align:left;"> 1.77240919693436 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> addItemToWork </td>
   <td style="text-align:right;"> 1,020 </td>
   <td style="text-align:right;"> 10,468 </td>
   <td style="text-align:left;"> 10.2627450980392 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> ownsWork </td>
   <td style="text-align:right;"> 510 </td>
   <td style="text-align:right;"> 5,641 </td>
   <td style="text-align:left;"> 11.0607843137255 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 503 </td>
   <td style="text-align:right;"> 24,855 </td>
   <td style="text-align:left;"> 49.4135188866799 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> searchAndConnect </td>
   <td style="text-align:right;"> 501 </td>
   <td style="text-align:right;"> 1,061,082 </td>
   <td style="text-align:left;"> 2117.92814371257 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> cycleSearch </td>
   <td style="text-align:right;"> 29 </td>
   <td style="text-align:right;"> 5,112 </td>
   <td style="text-align:left;"> 176.275862068966 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorks </td>
   <td style="text-align:right;"> 29 </td>
   <td style="text-align:right;"> 2,323 </td>
   <td style="text-align:left;"> 80.1034482758621 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> individualCycleSearch </td>
   <td style="text-align:right;"> 29 </td>
   <td style="text-align:right;"> 2 </td>
   <td style="text-align:left;"> 0.0689655172413793 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorksItems </td>
   <td style="text-align:right;"> 18 </td>
   <td style="text-align:right;"> 31 </td>
   <td style="text-align:left;"> 1.72222222222222 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 5,640 </td>
   <td style="text-align:right;font-weight: bold;"> 1,114,833 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  13 

<button class="btn btn-primary" data-toggle="collapse" data-target="#UHCDMZJZFV"> Show/Hide foundCycle query </button> 
 <div id="UHCDMZJZFV" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-05-39-kF9FXt--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  0 

<button class="btn btn-primary" data-toggle="collapse" data-target="#GWNOEOKTUV"> Show/Hide foundPath query </button> 
 <div id="GWNOEOKTUV" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-05-39-kF9FXt--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-05-05-57-fvqRNr--CV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 600 </td>
   <td style="text-align:left;width: 15em; "> 0.9 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 610 </td>
   <td style="text-align:right;width: 5em; "> 286,882 </td>
   <td style="text-align:right;width: 5em; "> 610 </td>
   <td style="text-align:right;width: 5em; "> 610 </td>
   <td style="text-align:right;width: 5em; "> 3,601 </td>
   <td style="text-align:right;width: 5em; "> 292,313 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 601 </td>
   <td style="text-align:right;width: 5em; "> 1,220 </td>
   <td style="text-align:right;width: 5em; "> 610 </td>
   <td style="text-align:right;width: 5em; "> 2,431 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-49.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-50.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-51.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> connectIfSimilar </td>
   <td style="text-align:right;"> 513,409 </td>
   <td style="text-align:right;"> 3,594,801 </td>
   <td style="text-align:left;"> 7.00182700342222 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> existsSimilarity </td>
   <td style="text-align:right;"> 513,409 </td>
   <td style="text-align:right;"> 3,581,120 </td>
   <td style="text-align:left;"> 6.97517963261259 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> similarityEdges </td>
   <td style="text-align:right;"> 513,409 </td>
   <td style="text-align:right;"> 3,507,544 </td>
   <td style="text-align:left;"> 6.83187088656412 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 603 </td>
   <td style="text-align:right;"> 30,075 </td>
   <td style="text-align:left;"> 49.8756218905473 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connectAllSimilar </td>
   <td style="text-align:right;"> 541 </td>
   <td style="text-align:right;"> 3,601,020 </td>
   <td style="text-align:left;"> 6656.2292051756 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connect </td>
   <td style="text-align:right;"> 220 </td>
   <td style="text-align:right;"> 710 </td>
   <td style="text-align:left;"> 3.22727272727273 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> cycleSearch </td>
   <td style="text-align:right;"> 32 </td>
   <td style="text-align:right;"> 843 </td>
   <td style="text-align:left;"> 26.34375 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorks </td>
   <td style="text-align:right;"> 31 </td>
   <td style="text-align:right;"> 78 </td>
   <td style="text-align:left;"> 2.51612903225806 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 1,541,654 </td>
   <td style="text-align:right;font-weight: bold;"> 14,316,191 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  1 

<button class="btn btn-primary" data-toggle="collapse" data-target="#DJGQEASANH"> Show/Hide foundCycle query </button> 
 <div id="DJGQEASANH" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-05-57-fvqRNr--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  0 

<button class="btn btn-primary" data-toggle="collapse" data-target="#ITSQIFTJSG"> Show/Hide foundPath query </button> 
 <div id="ITSQIFTJSG" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-05-57-fvqRNr--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-05-05-57-fvqRNr--DV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 600 </td>
   <td style="text-align:left;width: 15em; "> 0.9 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 610 </td>
   <td style="text-align:right;width: 5em; "> 286,442 </td>
   <td style="text-align:right;width: 5em; "> 610 </td>
   <td style="text-align:right;width: 5em; "> 610 </td>
   <td style="text-align:right;width: 5em; "> 3,601 </td>
   <td style="text-align:right;width: 5em; "> 291,873 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 601 </td>
   <td style="text-align:right;width: 5em; "> 1,220 </td>
   <td style="text-align:right;width: 5em; "> 610 </td>
   <td style="text-align:right;width: 5em; "> 2,431 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-52.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-53.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-54.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> knowsAgent </td>
   <td style="text-align:right;"> 3,601 </td>
   <td style="text-align:right;"> 6,832 </td>
   <td style="text-align:left;"> 1.89725076367676 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> addItemToWork </td>
   <td style="text-align:right;"> 1,220 </td>
   <td style="text-align:right;"> 10,512 </td>
   <td style="text-align:left;"> 8.61639344262295 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> ownsWork </td>
   <td style="text-align:right;"> 610 </td>
   <td style="text-align:right;"> 5,612 </td>
   <td style="text-align:left;"> 9.2 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 603 </td>
   <td style="text-align:right;"> 29,172 </td>
   <td style="text-align:left;"> 48.3781094527363 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> searchAndConnect </td>
   <td style="text-align:right;"> 601 </td>
   <td style="text-align:right;"> 4,034,268 </td>
   <td style="text-align:left;"> 6712.59234608985 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> individualCycleSearch </td>
   <td style="text-align:right;"> 114 </td>
   <td style="text-align:right;"> 5 </td>
   <td style="text-align:left;"> 0.043859649122807 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorks </td>
   <td style="text-align:right;"> 84 </td>
   <td style="text-align:right;"> 3,634 </td>
   <td style="text-align:left;"> 43.2619047619048 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> cycleSearch </td>
   <td style="text-align:right;"> 83 </td>
   <td style="text-align:right;"> 9,415 </td>
   <td style="text-align:left;"> 113.433734939759 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 6,916 </td>
   <td style="text-align:right;font-weight: bold;"> 4,099,450 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  40 

<button class="btn btn-primary" data-toggle="collapse" data-target="#WYMCJQHXFR"> Show/Hide foundCycle query </button> 
 <div id="WYMCJQHXFR" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-05-57-fvqRNr--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  0 

<button class="btn btn-primary" data-toggle="collapse" data-target="#JIMLMYIEAG"> Show/Hide foundPath query </button> 
 <div id="JIMLMYIEAG" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-05-57-fvqRNr--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-05-07-00-zrM33e--CV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 600 </td>
   <td style="text-align:left;width: 15em; "> 0.93 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 610 </td>
   <td style="text-align:right;width: 5em; "> 206,192 </td>
   <td style="text-align:right;width: 5em; "> 610 </td>
   <td style="text-align:right;width: 5em; "> 610 </td>
   <td style="text-align:right;width: 5em; "> 3,601 </td>
   <td style="text-align:right;width: 5em; "> 211,623 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 601 </td>
   <td style="text-align:right;width: 5em; "> 1,220 </td>
   <td style="text-align:right;width: 5em; "> 610 </td>
   <td style="text-align:right;width: 5em; "> 2,431 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-55.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-56.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-57.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> connectIfSimilar </td>
   <td style="text-align:right;"> 687,979 </td>
   <td style="text-align:right;"> 3,592,312 </td>
   <td style="text-align:left;"> 5.221543099426 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> existsSimilarity </td>
   <td style="text-align:right;"> 687,979 </td>
   <td style="text-align:right;"> 3,575,122 </td>
   <td style="text-align:left;"> 5.1965568716487 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> similarityEdges </td>
   <td style="text-align:right;"> 687,979 </td>
   <td style="text-align:right;"> 3,498,903 </td>
   <td style="text-align:left;"> 5.08577005984194 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connectAllSimilar </td>
   <td style="text-align:right;"> 886 </td>
   <td style="text-align:right;"> 3,600,530 </td>
   <td style="text-align:left;"> 4063.80361173815 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 603 </td>
   <td style="text-align:right;"> 29,601 </td>
   <td style="text-align:left;"> 49.089552238806 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connect </td>
   <td style="text-align:right;"> 223 </td>
   <td style="text-align:right;"> 677 </td>
   <td style="text-align:left;"> 3.03587443946188 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 2,065,649 </td>
   <td style="text-align:right;font-weight: bold;"> 14,297,145 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  1 

<button class="btn btn-primary" data-toggle="collapse" data-target="#GAPHTGQLAG"> Show/Hide foundCycle query </button> 
 <div id="GAPHTGQLAG" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-07-00-zrM33e--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  0 

<button class="btn btn-primary" data-toggle="collapse" data-target="#SKVHPXTZCS"> Show/Hide foundPath query </button> 
 <div id="SKVHPXTZCS" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-07-00-zrM33e--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-05-07-00-zrM33e--DV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 600 </td>
   <td style="text-align:left;width: 15em; "> 0.93 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 610 </td>
   <td style="text-align:right;width: 5em; "> 205,746 </td>
   <td style="text-align:right;width: 5em; "> 610 </td>
   <td style="text-align:right;width: 5em; "> 610 </td>
   <td style="text-align:right;width: 5em; "> 3,601 </td>
   <td style="text-align:right;width: 5em; "> 211,177 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 601 </td>
   <td style="text-align:right;width: 5em; "> 1,220 </td>
   <td style="text-align:right;width: 5em; "> 610 </td>
   <td style="text-align:right;width: 5em; "> 2,431 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-58.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-59.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-60.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> knowsAgent </td>
   <td style="text-align:right;"> 3,601 </td>
   <td style="text-align:right;"> 6,268 </td>
   <td style="text-align:left;"> 1.74062760344349 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> addItemToWork </td>
   <td style="text-align:right;"> 1,220 </td>
   <td style="text-align:right;"> 10,557 </td>
   <td style="text-align:left;"> 8.65327868852459 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> ownsWork </td>
   <td style="text-align:right;"> 610 </td>
   <td style="text-align:right;"> 5,031 </td>
   <td style="text-align:left;"> 8.24754098360656 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 603 </td>
   <td style="text-align:right;"> 29,898 </td>
   <td style="text-align:left;"> 49.5820895522388 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> searchAndConnect </td>
   <td style="text-align:right;"> 601 </td>
   <td style="text-align:right;"> 2,897,555 </td>
   <td style="text-align:left;"> 4821.22296173045 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> cycleSearch </td>
   <td style="text-align:right;"> 82 </td>
   <td style="text-align:right;"> 2,424 </td>
   <td style="text-align:left;"> 29.5609756097561 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorks </td>
   <td style="text-align:right;"> 82 </td>
   <td style="text-align:right;"> 2,569 </td>
   <td style="text-align:left;"> 31.3292682926829 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> individualCycleSearch </td>
   <td style="text-align:right;"> 82 </td>
   <td style="text-align:right;"> 0 </td>
   <td style="text-align:left;"> 0 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 6,881 </td>
   <td style="text-align:right;font-weight: bold;"> 2,954,302 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  35 

<button class="btn btn-primary" data-toggle="collapse" data-target="#JVDMYAGQON"> Show/Hide foundCycle query </button> 
 <div id="JVDMYAGQON" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-07-00-zrM33e--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  0 

<button class="btn btn-primary" data-toggle="collapse" data-target="#TSYWLYETMN"> Show/Hide foundPath query </button> 
 <div id="TSYWLYETMN" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-07-00-zrM33e--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-05-08-02-Lrzqji--CV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 600 </td>
   <td style="text-align:left;width: 15em; "> 0.96 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 610 </td>
   <td style="text-align:right;width: 5em; "> 119,194 </td>
   <td style="text-align:right;width: 5em; "> 610 </td>
   <td style="text-align:right;width: 5em; "> 610 </td>
   <td style="text-align:right;width: 5em; "> 3,601 </td>
   <td style="text-align:right;width: 5em; "> 124,625 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 601 </td>
   <td style="text-align:right;width: 5em; "> 1,220 </td>
   <td style="text-align:right;width: 5em; "> 610 </td>
   <td style="text-align:right;width: 5em; "> 2,431 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-61.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-62.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-63.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> connectIfSimilar </td>
   <td style="text-align:right;"> 743,590 </td>
   <td style="text-align:right;"> 2,768,156 </td>
   <td style="text-align:left;"> 3.72269126803749 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> existsSimilarity </td>
   <td style="text-align:right;"> 743,590 </td>
   <td style="text-align:right;"> 2,752,263 </td>
   <td style="text-align:left;"> 3.70131793058002 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> similarityEdges </td>
   <td style="text-align:right;"> 743,590 </td>
   <td style="text-align:right;"> 2,697,670 </td>
   <td style="text-align:left;"> 3.62789978348283 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connectAllSimilar </td>
   <td style="text-align:right;"> 1,220 </td>
   <td style="text-align:right;"> 2,776,344 </td>
   <td style="text-align:left;"> 2275.69180327869 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 603 </td>
   <td style="text-align:right;"> 30,480 </td>
   <td style="text-align:left;"> 50.547263681592 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connect </td>
   <td style="text-align:right;"> 113 </td>
   <td style="text-align:right;"> 334 </td>
   <td style="text-align:left;"> 2.95575221238938 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 2,232,706 </td>
   <td style="text-align:right;font-weight: bold;"> 11,025,247 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  1 

<button class="btn btn-primary" data-toggle="collapse" data-target="#HUQVGYBYQI"> Show/Hide foundCycle query </button> 
 <div id="HUQVGYBYQI" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-08-02-Lrzqji--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  0 

<button class="btn btn-primary" data-toggle="collapse" data-target="#QBDRQAOKYM"> Show/Hide foundPath query </button> 
 <div id="QBDRQAOKYM" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-08-02-Lrzqji--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-05-08-02-Lrzqji--DV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 600 </td>
   <td style="text-align:left;width: 15em; "> 0.96 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 610 </td>
   <td style="text-align:right;width: 5em; "> 118,968 </td>
   <td style="text-align:right;width: 5em; "> 610 </td>
   <td style="text-align:right;width: 5em; "> 610 </td>
   <td style="text-align:right;width: 5em; "> 3,601 </td>
   <td style="text-align:right;width: 5em; "> 124,399 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 601 </td>
   <td style="text-align:right;width: 5em; "> 1,220 </td>
   <td style="text-align:right;width: 5em; "> 610 </td>
   <td style="text-align:right;width: 5em; "> 2,431 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-64.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-65.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-66.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> knowsAgent </td>
   <td style="text-align:right;"> 3,601 </td>
   <td style="text-align:right;"> 6,368 </td>
   <td style="text-align:left;"> 1.76839766731463 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> addItemToWork </td>
   <td style="text-align:right;"> 1,220 </td>
   <td style="text-align:right;"> 13,502 </td>
   <td style="text-align:left;"> 11.0672131147541 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> ownsWork </td>
   <td style="text-align:right;"> 610 </td>
   <td style="text-align:right;"> 6,268 </td>
   <td style="text-align:left;"> 10.2754098360656 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 603 </td>
   <td style="text-align:right;"> 30,464 </td>
   <td style="text-align:left;"> 50.5207296849088 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> searchAndConnect </td>
   <td style="text-align:right;"> 601 </td>
   <td style="text-align:right;"> 2,167,574 </td>
   <td style="text-align:left;"> 3606.61231281198 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> cycleSearch </td>
   <td style="text-align:right;"> 61 </td>
   <td style="text-align:right;"> 2,962 </td>
   <td style="text-align:left;"> 48.5573770491803 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorks </td>
   <td style="text-align:right;"> 61 </td>
   <td style="text-align:right;"> 2,251 </td>
   <td style="text-align:left;"> 36.9016393442623 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> individualCycleSearch </td>
   <td style="text-align:right;"> 61 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 0.0163934426229508 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 6,818 </td>
   <td style="text-align:right;font-weight: bold;"> 2,229,390 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  25 

<button class="btn btn-primary" data-toggle="collapse" data-target="#JBSNAWJIEB"> Show/Hide foundCycle query </button> 
 <div id="JBSNAWJIEB" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-08-02-Lrzqji--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  0 

<button class="btn btn-primary" data-toggle="collapse" data-target="#NDXFEUFBWM"> Show/Hide foundPath query </button> 
 <div id="NDXFEUFBWM" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-08-02-Lrzqji--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-05-08-51-5PgQ8m--CV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 600 </td>
   <td style="text-align:left;width: 15em; "> 0.99 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 610 </td>
   <td style="text-align:right;width: 5em; "> 30,004 </td>
   <td style="text-align:right;width: 5em; "> 610 </td>
   <td style="text-align:right;width: 5em; "> 610 </td>
   <td style="text-align:right;width: 5em; "> 3,601 </td>
   <td style="text-align:right;width: 5em; "> 35,435 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 601 </td>
   <td style="text-align:right;width: 5em; "> 1,220 </td>
   <td style="text-align:right;width: 5em; "> 610 </td>
   <td style="text-align:right;width: 5em; "> 2,431 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-67.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-68.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-69.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> connectIfSimilar </td>
   <td style="text-align:right;"> 743,590 </td>
   <td style="text-align:right;"> 1,477,223 </td>
   <td style="text-align:left;"> 1.98660955634153 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> existsSimilarity </td>
   <td style="text-align:right;"> 743,590 </td>
   <td style="text-align:right;"> 1,464,298 </td>
   <td style="text-align:left;"> 1.96922766578356 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> similarityEdges </td>
   <td style="text-align:right;"> 743,590 </td>
   <td style="text-align:right;"> 1,437,974 </td>
   <td style="text-align:left;"> 1.93382643661157 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connectAllSimilar </td>
   <td style="text-align:right;"> 1,220 </td>
   <td style="text-align:right;"> 1,484,399 </td>
   <td style="text-align:left;"> 1216.72049180328 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 602 </td>
   <td style="text-align:right;"> 25,193 </td>
   <td style="text-align:left;"> 41.8488372093023 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connect </td>
   <td style="text-align:right;"> 35 </td>
   <td style="text-align:right;"> 152 </td>
   <td style="text-align:left;"> 4.34285714285714 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 2,232,627 </td>
   <td style="text-align:right;font-weight: bold;"> 5,889,239 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  1 

<button class="btn btn-primary" data-toggle="collapse" data-target="#DROAUDKDYS"> Show/Hide foundCycle query </button> 
 <div id="DROAUDKDYS" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-08-51-5PgQ8m--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  0 

<button class="btn btn-primary" data-toggle="collapse" data-target="#LWNAILFYKW"> Show/Hide foundPath query </button> 
 <div id="LWNAILFYKW" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-08-51-5PgQ8m--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-05-08-51-5PgQ8m--DV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 600 </td>
   <td style="text-align:left;width: 15em; "> 0.99 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 610 </td>
   <td style="text-align:right;width: 5em; "> 29,934 </td>
   <td style="text-align:right;width: 5em; "> 610 </td>
   <td style="text-align:right;width: 5em; "> 610 </td>
   <td style="text-align:right;width: 5em; "> 3,601 </td>
   <td style="text-align:right;width: 5em; "> 35,365 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 601 </td>
   <td style="text-align:right;width: 5em; "> 1,220 </td>
   <td style="text-align:right;width: 5em; "> 610 </td>
   <td style="text-align:right;width: 5em; "> 2,431 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-70.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-71.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-72.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> knowsAgent </td>
   <td style="text-align:right;"> 3,601 </td>
   <td style="text-align:right;"> 6,610 </td>
   <td style="text-align:left;"> 1.83560122188281 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> addItemToWork </td>
   <td style="text-align:right;"> 1,220 </td>
   <td style="text-align:right;"> 12,344 </td>
   <td style="text-align:left;"> 10.1180327868852 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> ownsWork </td>
   <td style="text-align:right;"> 610 </td>
   <td style="text-align:right;"> 5,682 </td>
   <td style="text-align:left;"> 9.31475409836066 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 603 </td>
   <td style="text-align:right;"> 31,554 </td>
   <td style="text-align:left;"> 52.3283582089552 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> searchAndConnect </td>
   <td style="text-align:right;"> 601 </td>
   <td style="text-align:right;"> 1,391,528 </td>
   <td style="text-align:left;"> 2315.3544093178 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> cycleSearch </td>
   <td style="text-align:right;"> 46 </td>
   <td style="text-align:right;"> 16,711 </td>
   <td style="text-align:left;"> 363.282608695652 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorks </td>
   <td style="text-align:right;"> 46 </td>
   <td style="text-align:right;"> 4,444 </td>
   <td style="text-align:left;"> 96.6086956521739 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> individualCycleSearch </td>
   <td style="text-align:right;"> 46 </td>
   <td style="text-align:right;"> 2 </td>
   <td style="text-align:left;"> 0.0434782608695652 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 6,773 </td>
   <td style="text-align:right;font-weight: bold;"> 1,468,875 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  18 

<button class="btn btn-primary" data-toggle="collapse" data-target="#KXVZFJVOBP"> Show/Hide foundCycle query </button> 
 <div id="KXVZFJVOBP" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-08-51-5PgQ8m--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  7 

<button class="btn btn-primary" data-toggle="collapse" data-target="#CKLTQAZMTV"> Show/Hide foundPath query </button> 
 <div id="CKLTQAZMTV" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-08-51-5PgQ8m--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
## Scatterplots of experiment 

<img src="index_files/figure-html/get-experiment-info-73.png" width="672" /><img src="index_files/figure-html/get-experiment-info-74.png" width="672" /><img src="index_files/figure-html/get-experiment-info-75.png" width="672" /><img src="index_files/figure-html/get-experiment-info-76.png" width="672" /><img src="index_files/figure-html/get-experiment-info-77.png" width="672" /><img src="index_files/figure-html/get-experiment-info-78.png" width="672" /><img src="index_files/figure-html/get-experiment-info-79.png" width="672" /><img src="index_files/figure-html/get-experiment-info-80.png" width="672" /><img src="index_files/figure-html/get-experiment-info-81.png" width="672" /><img src="index_files/figure-html/get-experiment-info-82.png" width="672" /><img src="index_files/figure-html/get-experiment-info-83.png" width="672" /><img src="index_files/figure-html/get-experiment-info-84.png" width="672" />   

# Experiment  2 

Same as above, only agentNumbers=[700, 800] 

## Experimental set up 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;">   </th>
   <th style="text-align:left;"> Values </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> experimentId </td>
   <td style="text-align:left;"> EXP09-05-11-49-gcWcHs </td>
  </tr>
  <tr>
   <td style="text-align:left;"> agentNumbers </td>
   <td style="text-align:left;"> 700, 800 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> maxDistances </td>
   <td style="text-align:left;"> 10 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> randomWorksNumberMultipliers </td>
   <td style="text-align:left;"> 1 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> similarityConnectThresholds </td>
   <td style="text-align:left;"> 0.9, 0.93, 0.96, 0.99 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> chainLengths </td>
   <td style="text-align:left;"> 10 </td>
  </tr>
</tbody>
</table>
## Descriptive analysis of each simulation

###  SIM09-05-11-49-Zfitmw--CV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 700 </td>
   <td style="text-align:left;width: 15em; "> 0.9 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 710 </td>
   <td style="text-align:right;width: 5em; "> 390,090 </td>
   <td style="text-align:right;width: 5em; "> 710 </td>
   <td style="text-align:right;width: 5em; "> 710 </td>
   <td style="text-align:right;width: 5em; "> 4,201 </td>
   <td style="text-align:right;width: 5em; "> 396,421 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 701 </td>
   <td style="text-align:right;width: 5em; "> 1,420 </td>
   <td style="text-align:right;width: 5em; "> 710 </td>
   <td style="text-align:right;width: 5em; "> 2,831 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-85.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-86.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-87.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> connectIfSimilar </td>
   <td style="text-align:right;"> 451,505 </td>
   <td style="text-align:right;"> 3,602,277 </td>
   <td style="text-align:left;"> 7.9783767621621 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> existsSimilarity </td>
   <td style="text-align:right;"> 451,505 </td>
   <td style="text-align:right;"> 3,589,468 </td>
   <td style="text-align:left;"> 7.95000719814841 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> similarityEdges </td>
   <td style="text-align:right;"> 451,505 </td>
   <td style="text-align:right;"> 3,513,532 </td>
   <td style="text-align:left;"> 7.78182301414159 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 703 </td>
   <td style="text-align:right;"> 70,533 </td>
   <td style="text-align:left;"> 100.331436699858 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connectAllSimilar </td>
   <td style="text-align:right;"> 365 </td>
   <td style="text-align:right;"> 3,607,853 </td>
   <td style="text-align:left;"> 9884.52876712329 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connect </td>
   <td style="text-align:right;"> 188 </td>
   <td style="text-align:right;"> 659 </td>
   <td style="text-align:left;"> 3.50531914893617 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> cycleSearch </td>
   <td style="text-align:right;"> 92 </td>
   <td style="text-align:right;"> 8,044 </td>
   <td style="text-align:left;"> 87.4347826086957 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorks </td>
   <td style="text-align:right;"> 91 </td>
   <td style="text-align:right;"> 766 </td>
   <td style="text-align:left;"> 8.41758241758242 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> searchAndConnect </td>
   <td style="text-align:right;"> 23 </td>
   <td style="text-align:right;"> 232,377 </td>
   <td style="text-align:left;"> 10103.347826087 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 1,355,977 </td>
   <td style="text-align:right;font-weight: bold;"> 14,625,509 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  1 

<button class="btn btn-primary" data-toggle="collapse" data-target="#FCUFNSKSUX"> Show/Hide foundCycle query </button> 
 <div id="FCUFNSKSUX" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-11-49-Zfitmw--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  0 

<button class="btn btn-primary" data-toggle="collapse" data-target="#LCTZBALVQM"> Show/Hide foundPath query </button> 
 <div id="LCTZBALVQM" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-11-49-Zfitmw--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-05-11-49-Zfitmw--DV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 700 </td>
   <td style="text-align:left;width: 15em; "> 0.9 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 710 </td>
   <td style="text-align:right;width: 5em; "> 389,714 </td>
   <td style="text-align:right;width: 5em; "> 710 </td>
   <td style="text-align:right;width: 5em; "> 710 </td>
   <td style="text-align:right;width: 5em; "> 4,201 </td>
   <td style="text-align:right;width: 5em; "> 396,045 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 701 </td>
   <td style="text-align:right;width: 5em; "> 1,420 </td>
   <td style="text-align:right;width: 5em; "> 710 </td>
   <td style="text-align:right;width: 5em; "> 2,831 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-88.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-89.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-90.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> knowsAgent </td>
   <td style="text-align:right;"> 4,201 </td>
   <td style="text-align:right;"> 10,566 </td>
   <td style="text-align:left;"> 2.51511544870269 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> addItemToWork </td>
   <td style="text-align:right;"> 1,420 </td>
   <td style="text-align:right;"> 26,213 </td>
   <td style="text-align:left;"> 18.4598591549296 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> ownsWork </td>
   <td style="text-align:right;"> 710 </td>
   <td style="text-align:right;"> 12,539 </td>
   <td style="text-align:left;"> 17.6605633802817 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 703 </td>
   <td style="text-align:right;"> 70,641 </td>
   <td style="text-align:left;"> 100.48506401138 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> searchAndConnect </td>
   <td style="text-align:right;"> 678 </td>
   <td style="text-align:right;"> 5,468,733 </td>
   <td style="text-align:left;"> 8065.97787610619 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> individualCycleSearch </td>
   <td style="text-align:right;"> 97 </td>
   <td style="text-align:right;"> 12 </td>
   <td style="text-align:left;"> 0.123711340206186 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorksItems </td>
   <td style="text-align:right;"> 18 </td>
   <td style="text-align:right;"> 62 </td>
   <td style="text-align:left;"> 3.44444444444444 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorks </td>
   <td style="text-align:right;"> 7 </td>
   <td style="text-align:right;"> 29,416 </td>
   <td style="text-align:left;"> 4202.28571428571 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> cycleSearch </td>
   <td style="text-align:right;"> 6 </td>
   <td style="text-align:right;"> 40,539 </td>
   <td style="text-align:left;"> 6756.5 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 7,840 </td>
   <td style="text-align:right;font-weight: bold;"> 5,658,721 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  3 

<button class="btn btn-primary" data-toggle="collapse" data-target="#XQXJNWDANL"> Show/Hide foundCycle query </button> 
 <div id="XQXJNWDANL" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-11-49-Zfitmw--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  0 

<button class="btn btn-primary" data-toggle="collapse" data-target="#CIJFBGTEXX"> Show/Hide foundPath query </button> 
 <div id="CIJFBGTEXX" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-05-11-49-Zfitmw--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-06-01-57-weh222--CV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 700 </td>
   <td style="text-align:left;width: 15em; "> 0.96 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 710 </td>
   <td style="text-align:right;width: 5em; "> 161,698 </td>
   <td style="text-align:right;width: 5em; "> 710 </td>
   <td style="text-align:right;width: 5em; "> 710 </td>
   <td style="text-align:right;width: 5em; "> 4,201 </td>
   <td style="text-align:right;width: 5em; "> 168,029 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 701 </td>
   <td style="text-align:right;width: 5em; "> 1,420 </td>
   <td style="text-align:right;width: 5em; "> 710 </td>
   <td style="text-align:right;width: 5em; "> 2,831 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-91.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-92.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-93.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> connectIfSimilar </td>
   <td style="text-align:right;"> 861,420 </td>
   <td style="text-align:right;"> 3,590,255 </td>
   <td style="text-align:left;"> 4.16783334494207 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> existsSimilarity </td>
   <td style="text-align:right;"> 861,420 </td>
   <td style="text-align:right;"> 3,570,207 </td>
   <td style="text-align:left;"> 4.14456014487706 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> similarityEdges </td>
   <td style="text-align:right;"> 861,420 </td>
   <td style="text-align:right;"> 3,499,119 </td>
   <td style="text-align:left;"> 4.06203594065613 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connectAllSimilar </td>
   <td style="text-align:right;"> 879 </td>
   <td style="text-align:right;"> 3,600,434 </td>
   <td style="text-align:left;"> 4096.05688282139 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 703 </td>
   <td style="text-align:right;"> 31,495 </td>
   <td style="text-align:left;"> 44.800853485064 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connect </td>
   <td style="text-align:right;"> 149 </td>
   <td style="text-align:right;"> 378 </td>
   <td style="text-align:left;"> 2.53691275167785 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 2,585,991 </td>
   <td style="text-align:right;font-weight: bold;"> 14,291,888 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  1 

<button class="btn btn-primary" data-toggle="collapse" data-target="#WYOUWICHOP"> Show/Hide foundCycle query </button> 
 <div id="WYOUWICHOP" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-06-01-57-weh222--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  0 

<button class="btn btn-primary" data-toggle="collapse" data-target="#PGDEBTBOET"> Show/Hide foundPath query </button> 
 <div id="PGDEBTBOET" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-06-01-57-weh222--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-06-01-57-weh222--DV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 700 </td>
   <td style="text-align:left;width: 15em; "> 0.96 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 710 </td>
   <td style="text-align:right;width: 5em; "> 161,400 </td>
   <td style="text-align:right;width: 5em; "> 710 </td>
   <td style="text-align:right;width: 5em; "> 710 </td>
   <td style="text-align:right;width: 5em; "> 4,201 </td>
   <td style="text-align:right;width: 5em; "> 167,731 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 701 </td>
   <td style="text-align:right;width: 5em; "> 1,420 </td>
   <td style="text-align:right;width: 5em; "> 710 </td>
   <td style="text-align:right;width: 5em; "> 2,831 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-94.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-95.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-96.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> knowsAgent </td>
   <td style="text-align:right;"> 4,201 </td>
   <td style="text-align:right;"> 7,625 </td>
   <td style="text-align:left;"> 1.81504403713402 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> addItemToWork </td>
   <td style="text-align:right;"> 1,420 </td>
   <td style="text-align:right;"> 15,522 </td>
   <td style="text-align:left;"> 10.930985915493 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> ownsWork </td>
   <td style="text-align:right;"> 710 </td>
   <td style="text-align:right;"> 6,716 </td>
   <td style="text-align:left;"> 9.45915492957747 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 703 </td>
   <td style="text-align:right;"> 30,839 </td>
   <td style="text-align:left;"> 43.8677098150782 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> searchAndConnect </td>
   <td style="text-align:right;"> 701 </td>
   <td style="text-align:right;"> 2,953,311 </td>
   <td style="text-align:left;"> 4212.99714693295 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> cycleSearch </td>
   <td style="text-align:right;"> 83 </td>
   <td style="text-align:right;"> 3,031 </td>
   <td style="text-align:left;"> 36.5180722891566 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorks </td>
   <td style="text-align:right;"> 83 </td>
   <td style="text-align:right;"> 3,415 </td>
   <td style="text-align:left;"> 41.144578313253 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> individualCycleSearch </td>
   <td style="text-align:right;"> 83 </td>
   <td style="text-align:right;"> 5 </td>
   <td style="text-align:left;"> 0.0602409638554217 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 7,984 </td>
   <td style="text-align:right;font-weight: bold;"> 3,020,464 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  30 

<button class="btn btn-primary" data-toggle="collapse" data-target="#PGSQTNUUPI"> Show/Hide foundCycle query </button> 
 <div id="PGSQTNUUPI" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-06-01-57-weh222--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  0 

<button class="btn btn-primary" data-toggle="collapse" data-target="#CPUMQOUKYL"> Show/Hide foundPath query </button> 
 <div id="CPUMQOUKYL" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-06-01-57-weh222--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-06-02-59-TNfsQt--CV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 700 </td>
   <td style="text-align:left;width: 15em; "> 0.99 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 710 </td>
   <td style="text-align:right;width: 5em; "> 40,964 </td>
   <td style="text-align:right;width: 5em; "> 710 </td>
   <td style="text-align:right;width: 5em; "> 710 </td>
   <td style="text-align:right;width: 5em; "> 4,201 </td>
   <td style="text-align:right;width: 5em; "> 47,295 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 701 </td>
   <td style="text-align:right;width: 5em; "> 1,420 </td>
   <td style="text-align:right;width: 5em; "> 710 </td>
   <td style="text-align:right;width: 5em; "> 2,831 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-97.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-98.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-99.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> connectIfSimilar </td>
   <td style="text-align:right;"> 1,007,490 </td>
   <td style="text-align:right;"> 2,141,953 </td>
   <td style="text-align:left;"> 2.12602904247189 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> existsSimilarity </td>
   <td style="text-align:right;"> 1,007,490 </td>
   <td style="text-align:right;"> 2,123,923 </td>
   <td style="text-align:left;"> 2.10813308320678 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> similarityEdges </td>
   <td style="text-align:right;"> 1,007,490 </td>
   <td style="text-align:right;"> 2,085,646 </td>
   <td style="text-align:left;"> 2.07014064655729 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connectAllSimilar </td>
   <td style="text-align:right;"> 1,420 </td>
   <td style="text-align:right;"> 2,151,600 </td>
   <td style="text-align:left;"> 1515.21126760563 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 703 </td>
   <td style="text-align:right;"> 31,868 </td>
   <td style="text-align:left;"> 45.3314366998578 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connect </td>
   <td style="text-align:right;"> 51 </td>
   <td style="text-align:right;"> 86 </td>
   <td style="text-align:left;"> 1.68627450980392 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 3,024,644 </td>
   <td style="text-align:right;font-weight: bold;"> 8,535,076 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  1 

<button class="btn btn-primary" data-toggle="collapse" data-target="#VKMWXBZFKG"> Show/Hide foundCycle query </button> 
 <div id="VKMWXBZFKG" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-06-02-59-TNfsQt--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  0 

<button class="btn btn-primary" data-toggle="collapse" data-target="#ZSRIIZUXLP"> Show/Hide foundPath query </button> 
 <div id="ZSRIIZUXLP" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-06-02-59-TNfsQt--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-06-02-59-TNfsQt--DV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 700 </td>
   <td style="text-align:left;width: 15em; "> 0.99 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 710 </td>
   <td style="text-align:right;width: 5em; "> 40,862 </td>
   <td style="text-align:right;width: 5em; "> 710 </td>
   <td style="text-align:right;width: 5em; "> 710 </td>
   <td style="text-align:right;width: 5em; "> 4,201 </td>
   <td style="text-align:right;width: 5em; "> 47,193 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 701 </td>
   <td style="text-align:right;width: 5em; "> 1,420 </td>
   <td style="text-align:right;width: 5em; "> 710 </td>
   <td style="text-align:right;width: 5em; "> 2,831 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-100.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-101.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-102.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;">   </th>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> 1 </td>
   <td style="text-align:left;"> knowsAgent </td>
   <td style="text-align:right;"> 4,201 </td>
   <td style="text-align:right;"> 8,065 </td>
   <td style="text-align:left;"> 1.91978100452273 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> 2 </td>
   <td style="text-align:left;"> addItemToWork </td>
   <td style="text-align:right;"> 1,420 </td>
   <td style="text-align:right;"> 15,288 </td>
   <td style="text-align:left;"> 10.7661971830986 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> 3 </td>
   <td style="text-align:left;"> ownsWork </td>
   <td style="text-align:right;"> 710 </td>
   <td style="text-align:right;"> 7,345 </td>
   <td style="text-align:left;"> 10.3450704225352 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> 4 </td>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 703 </td>
   <td style="text-align:right;"> 31,828 </td>
   <td style="text-align:left;"> 45.2745376955903 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> 5 </td>
   <td style="text-align:left;"> searchAndConnect </td>
   <td style="text-align:right;"> 701 </td>
   <td style="text-align:right;"> 2,247,385 </td>
   <td style="text-align:left;"> 3205.97004279601 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> 6 </td>
   <td style="text-align:left;"> cycleSearch </td>
   <td style="text-align:right;"> 63 </td>
   <td style="text-align:right;"> 4,026 </td>
   <td style="text-align:left;"> 63.9047619047619 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> 7 </td>
   <td style="text-align:left;"> getWorks </td>
   <td style="text-align:right;"> 63 </td>
   <td style="text-align:right;"> 3,401 </td>
   <td style="text-align:left;"> 53.984126984127 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> 8 </td>
   <td style="text-align:left;"> individualCycleSearch </td>
   <td style="text-align:right;"> 63 </td>
   <td style="text-align:right;"> 2 </td>
   <td style="text-align:left;"> 0.0317460317460317 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:left;"> getWorksItems </td>
   <td style="text-align:right;"> 18 </td>
   <td style="text-align:right;"> 51 </td>
   <td style="text-align:left;"> 2.83333333333333 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> 11 </td>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 7,942 </td>
   <td style="text-align:right;font-weight: bold;"> 2,317,391 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  18 

<button class="btn btn-primary" data-toggle="collapse" data-target="#FKHIDNGKLO"> Show/Hide foundCycle query </button> 
 <div id="FKHIDNGKLO" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-06-02-59-TNfsQt--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  0 

<button class="btn btn-primary" data-toggle="collapse" data-target="#XSNILPWRBA"> Show/Hide foundPath query </button> 
 <div id="XSNILPWRBA" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-06-02-59-TNfsQt--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-06-12-53-gtcCvK--CV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 700 </td>
   <td style="text-align:left;width: 15em; "> 0.93 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 710 </td>
   <td style="text-align:right;width: 5em; "> 278,904 </td>
   <td style="text-align:right;width: 5em; "> 710 </td>
   <td style="text-align:right;width: 5em; "> 710 </td>
   <td style="text-align:right;width: 5em; "> 4,201 </td>
   <td style="text-align:right;width: 5em; "> 285,235 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 701 </td>
   <td style="text-align:right;width: 5em; "> 1,420 </td>
   <td style="text-align:right;width: 5em; "> 710 </td>
   <td style="text-align:right;width: 5em; "> 2,831 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-103.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-104.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-105.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> connectIfSimilar </td>
   <td style="text-align:right;"> 592,985 </td>
   <td style="text-align:right;"> 3,595,919 </td>
   <td style="text-align:left;"> 6.06409774277596 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> existsSimilarity </td>
   <td style="text-align:right;"> 592,985 </td>
   <td style="text-align:right;"> 3,580,475 </td>
   <td style="text-align:left;"> 6.03805323912072 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> similarityEdges </td>
   <td style="text-align:right;"> 592,985 </td>
   <td style="text-align:right;"> 3,506,195 </td>
   <td style="text-align:left;"> 5.91278868774084 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 703 </td>
   <td style="text-align:right;"> 30,087 </td>
   <td style="text-align:left;"> 42.7980085348506 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connectAllSimilar </td>
   <td style="text-align:right;"> 509 </td>
   <td style="text-align:right;"> 3,603,218 </td>
   <td style="text-align:left;"> 7079.0137524558 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connect </td>
   <td style="text-align:right;"> 143 </td>
   <td style="text-align:right;"> 395 </td>
   <td style="text-align:left;"> 2.76223776223776 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> cycleSearch </td>
   <td style="text-align:right;"> 37 </td>
   <td style="text-align:right;"> 929 </td>
   <td style="text-align:left;"> 25.1081081081081 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorks </td>
   <td style="text-align:right;"> 36 </td>
   <td style="text-align:right;"> 82 </td>
   <td style="text-align:left;"> 2.27777777777778 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 1,780,383 </td>
   <td style="text-align:right;font-weight: bold;"> 14,317,300 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  1 

<button class="btn btn-primary" data-toggle="collapse" data-target="#CZCDBDVZQG"> Show/Hide foundCycle query </button> 
 <div id="CZCDBDVZQG" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-06-12-53-gtcCvK--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  0 

<button class="btn btn-primary" data-toggle="collapse" data-target="#LVKTAKIEJI"> Show/Hide foundPath query </button> 
 <div id="LVKTAKIEJI" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-06-12-53-gtcCvK--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-06-12-53-gtcCvK--DV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 700 </td>
   <td style="text-align:left;width: 15em; "> 0.93 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 710 </td>
   <td style="text-align:right;width: 5em; "> 278,618 </td>
   <td style="text-align:right;width: 5em; "> 710 </td>
   <td style="text-align:right;width: 5em; "> 710 </td>
   <td style="text-align:right;width: 5em; "> 4,201 </td>
   <td style="text-align:right;width: 5em; "> 284,949 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 701 </td>
   <td style="text-align:right;width: 5em; "> 1,420 </td>
   <td style="text-align:right;width: 5em; "> 710 </td>
   <td style="text-align:right;width: 5em; "> 2,831 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-106.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-107.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-108.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> knowsAgent </td>
   <td style="text-align:right;"> 4,201 </td>
   <td style="text-align:right;"> 8,402 </td>
   <td style="text-align:left;"> 2 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> addItemToWork </td>
   <td style="text-align:right;"> 1,420 </td>
   <td style="text-align:right;"> 11,414 </td>
   <td style="text-align:left;"> 8.03802816901409 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> ownsWork </td>
   <td style="text-align:right;"> 710 </td>
   <td style="text-align:right;"> 5,545 </td>
   <td style="text-align:left;"> 7.80985915492958 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 703 </td>
   <td style="text-align:right;"> 30,473 </td>
   <td style="text-align:left;"> 43.3470839260313 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> searchAndConnect </td>
   <td style="text-align:right;"> 701 </td>
   <td style="text-align:right;"> 4,319,698 </td>
   <td style="text-align:left;"> 6162.1940085592 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> individualCycleSearch </td>
   <td style="text-align:right;"> 122 </td>
   <td style="text-align:right;"> 7 </td>
   <td style="text-align:left;"> 0.0573770491803279 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorks </td>
   <td style="text-align:right;"> 87 </td>
   <td style="text-align:right;"> 5,380 </td>
   <td style="text-align:left;"> 61.8390804597701 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> cycleSearch </td>
   <td style="text-align:right;"> 86 </td>
   <td style="text-align:right;"> 2,844 </td>
   <td style="text-align:left;"> 33.0697674418605 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 8,030 </td>
   <td style="text-align:right;font-weight: bold;"> 4,383,763 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  41 

<button class="btn btn-primary" data-toggle="collapse" data-target="#UFWDZAQXCU"> Show/Hide foundCycle query </button> 
 <div id="UFWDZAQXCU" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-06-12-53-gtcCvK--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  0 

<button class="btn btn-primary" data-toggle="collapse" data-target="#PFWNKNHUUI"> Show/Hide foundPath query </button> 
 <div id="PFWNKNHUUI" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-06-12-53-gtcCvK--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-06-03-37-UDs3YI--CV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 800 </td>
   <td style="text-align:left;width: 15em; "> 0.9 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 810 </td>
   <td style="text-align:right;width: 5em; "> 507,696 </td>
   <td style="text-align:right;width: 5em; "> 810 </td>
   <td style="text-align:right;width: 5em; "> 810 </td>
   <td style="text-align:right;width: 5em; "> 4,801 </td>
   <td style="text-align:right;width: 5em; "> 514,927 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 801 </td>
   <td style="text-align:right;width: 5em; "> 1,620 </td>
   <td style="text-align:right;width: 5em; "> 810 </td>
   <td style="text-align:right;width: 5em; "> 3,231 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-109.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-110.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-111.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> connectIfSimilar </td>
   <td style="text-align:right;"> 395,409 </td>
   <td style="text-align:right;"> 3,602,230 </td>
   <td style="text-align:left;"> 9.11013659274321 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> existsSimilarity </td>
   <td style="text-align:right;"> 395,409 </td>
   <td style="text-align:right;"> 3,590,324 </td>
   <td style="text-align:left;"> 9.0800259983966 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> similarityEdges </td>
   <td style="text-align:right;"> 395,409 </td>
   <td style="text-align:right;"> 3,514,353 </td>
   <td style="text-align:left;"> 8.88789329529677 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 803 </td>
   <td style="text-align:right;"> 74,913 </td>
   <td style="text-align:left;"> 93.2914072229141 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connectAllSimilar </td>
   <td style="text-align:right;"> 266 </td>
   <td style="text-align:right;"> 3,607,307 </td>
   <td style="text-align:left;"> 13561.3045112782 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> cycleSearch </td>
   <td style="text-align:right;"> 168 </td>
   <td style="text-align:right;"> 7,604 </td>
   <td style="text-align:left;"> 45.2619047619048 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorks </td>
   <td style="text-align:right;"> 167 </td>
   <td style="text-align:right;"> 710 </td>
   <td style="text-align:left;"> 4.25149700598802 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connect </td>
   <td style="text-align:right;"> 146 </td>
   <td style="text-align:right;"> 580 </td>
   <td style="text-align:left;"> 3.97260273972603 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> searchAndConnect </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 159,035 </td>
   <td style="text-align:left;"> 15903.5 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 1,187,787 </td>
   <td style="text-align:right;font-weight: bold;"> 14,557,056 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  1 

<button class="btn btn-primary" data-toggle="collapse" data-target="#EHWRLIHKMY"> Show/Hide foundCycle query </button> 
 <div id="EHWRLIHKMY" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-06-03-37-UDs3YI--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  0 

<button class="btn btn-primary" data-toggle="collapse" data-target="#UEHJIQTBDR"> Show/Hide foundPath query </button> 
 <div id="UEHJIQTBDR" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-06-03-37-UDs3YI--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-06-03-37-UDs3YI--DV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 800 </td>
   <td style="text-align:left;width: 15em; "> 0.9 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 810 </td>
   <td style="text-align:right;width: 5em; "> 507,393 </td>
   <td style="text-align:right;width: 5em; "> 810 </td>
   <td style="text-align:right;width: 5em; "> 810 </td>
   <td style="text-align:right;width: 5em; "> 4,801 </td>
   <td style="text-align:right;width: 5em; "> 514,624 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 801 </td>
   <td style="text-align:right;width: 5em; "> 1,620 </td>
   <td style="text-align:right;width: 5em; "> 810 </td>
   <td style="text-align:right;width: 5em; "> 3,231 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-112.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-113.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-114.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> knowsAgent </td>
   <td style="text-align:right;"> 4,801 </td>
   <td style="text-align:right;"> 8,963 </td>
   <td style="text-align:left;"> 1.86690272859821 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> addItemToWork </td>
   <td style="text-align:right;"> 1,620 </td>
   <td style="text-align:right;"> 12,801 </td>
   <td style="text-align:left;"> 7.90185185185185 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> ownsWork </td>
   <td style="text-align:right;"> 810 </td>
   <td style="text-align:right;"> 6,400 </td>
   <td style="text-align:left;"> 7.90123456790123 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 803 </td>
   <td style="text-align:right;"> 72,008 </td>
   <td style="text-align:left;"> 89.6737235367372 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> searchAndConnect </td>
   <td style="text-align:right;"> 791 </td>
   <td style="text-align:right;"> 8,247,566 </td>
   <td style="text-align:left;"> 10426.7585335019 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> individualCycleSearch </td>
   <td style="text-align:right;"> 172 </td>
   <td style="text-align:right;"> 3 </td>
   <td style="text-align:left;"> 0.0174418604651163 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorksItems </td>
   <td style="text-align:right;"> 18 </td>
   <td style="text-align:right;"> 29 </td>
   <td style="text-align:left;"> 1.61111111111111 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorks </td>
   <td style="text-align:right;"> 6 </td>
   <td style="text-align:right;"> 28,008 </td>
   <td style="text-align:left;"> 4668 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> cycleSearch </td>
   <td style="text-align:right;"> 5 </td>
   <td style="text-align:right;"> 34,439 </td>
   <td style="text-align:left;"> 6887.8 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 9,026 </td>
   <td style="text-align:right;font-weight: bold;"> 8,410,217 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  3 

<button class="btn btn-primary" data-toggle="collapse" data-target="#VDGAZMZEEG"> Show/Hide foundCycle query </button> 
 <div id="VDGAZMZEEG" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-06-03-37-UDs3YI--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  0 

<button class="btn btn-primary" data-toggle="collapse" data-target="#GSAAIGXPGP"> Show/Hide foundPath query </button> 
 <div id="GSAAIGXPGP" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-06-03-37-UDs3YI--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-06-04-43-xhtRRy--CV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 800 </td>
   <td style="text-align:left;width: 15em; "> 0.93 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 810 </td>
   <td style="text-align:right;width: 5em; "> 359,148 </td>
   <td style="text-align:right;width: 5em; "> 810 </td>
   <td style="text-align:right;width: 5em; "> 810 </td>
   <td style="text-align:right;width: 5em; "> 4,801 </td>
   <td style="text-align:right;width: 5em; "> 366,379 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 801 </td>
   <td style="text-align:right;width: 5em; "> 1,620 </td>
   <td style="text-align:right;width: 5em; "> 810 </td>
   <td style="text-align:right;width: 5em; "> 3,231 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-115.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-116.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-117.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> connectIfSimilar </td>
   <td style="text-align:right;"> 538,244 </td>
   <td style="text-align:right;"> 3,601,387 </td>
   <td style="text-align:left;"> 6.69099330415202 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> existsSimilarity </td>
   <td style="text-align:right;"> 538,244 </td>
   <td style="text-align:right;"> 3,586,920 </td>
   <td style="text-align:left;"> 6.66411515966736 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> similarityEdges </td>
   <td style="text-align:right;"> 538,244 </td>
   <td style="text-align:right;"> 3,512,694 </td>
   <td style="text-align:left;"> 6.52621116073751 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 803 </td>
   <td style="text-align:right;"> 57,563 </td>
   <td style="text-align:left;"> 71.6849315068493 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connectAllSimilar </td>
   <td style="text-align:right;"> 376 </td>
   <td style="text-align:right;"> 3,608,016 </td>
   <td style="text-align:left;"> 9595.78723404255 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connect </td>
   <td style="text-align:right;"> 132 </td>
   <td style="text-align:right;"> 585 </td>
   <td style="text-align:left;"> 4.43181818181818 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> cycleSearch </td>
   <td style="text-align:right;"> 115 </td>
   <td style="text-align:right;"> 4,371 </td>
   <td style="text-align:left;"> 38.0086956521739 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorks </td>
   <td style="text-align:right;"> 114 </td>
   <td style="text-align:right;"> 720 </td>
   <td style="text-align:left;"> 6.31578947368421 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> searchAndConnect </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 79,564 </td>
   <td style="text-align:left;"> 7956.4 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 1,616,282 </td>
   <td style="text-align:right;font-weight: bold;"> 14,451,820 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  1 

<button class="btn btn-primary" data-toggle="collapse" data-target="#NQXWKAHVYU"> Show/Hide foundCycle query </button> 
 <div id="NQXWKAHVYU" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-06-04-43-xhtRRy--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  0 

<button class="btn btn-primary" data-toggle="collapse" data-target="#NNRRSDYEOK"> Show/Hide foundPath query </button> 
 <div id="NNRRSDYEOK" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-06-04-43-xhtRRy--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-06-04-43-xhtRRy--DV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 800 </td>
   <td style="text-align:left;width: 15em; "> 0.93 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 810 </td>
   <td style="text-align:right;width: 5em; "> 358,884 </td>
   <td style="text-align:right;width: 5em; "> 810 </td>
   <td style="text-align:right;width: 5em; "> 810 </td>
   <td style="text-align:right;width: 5em; "> 4,801 </td>
   <td style="text-align:right;width: 5em; "> 366,115 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 801 </td>
   <td style="text-align:right;width: 5em; "> 1,620 </td>
   <td style="text-align:right;width: 5em; "> 810 </td>
   <td style="text-align:right;width: 5em; "> 3,231 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-118.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-119.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-120.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> knowsAgent </td>
   <td style="text-align:right;"> 4,801 </td>
   <td style="text-align:right;"> 9,098 </td>
   <td style="text-align:left;"> 1.89502187044366 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> addItemToWork </td>
   <td style="text-align:right;"> 1,620 </td>
   <td style="text-align:right;"> 18,118 </td>
   <td style="text-align:left;"> 11.183950617284 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> ownsWork </td>
   <td style="text-align:right;"> 810 </td>
   <td style="text-align:right;"> 7,401 </td>
   <td style="text-align:left;"> 9.13703703703704 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 803 </td>
   <td style="text-align:right;"> 55,182 </td>
   <td style="text-align:left;"> 68.719800747198 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> searchAndConnect </td>
   <td style="text-align:right;"> 791 </td>
   <td style="text-align:right;"> 5,602,947 </td>
   <td style="text-align:left;"> 7083.37168141593 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> individualCycleSearch </td>
   <td style="text-align:right;"> 124 </td>
   <td style="text-align:right;"> 3 </td>
   <td style="text-align:left;"> 0.0241935483870968 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorksItems </td>
   <td style="text-align:right;"> 18 </td>
   <td style="text-align:right;"> 38 </td>
   <td style="text-align:left;"> 2.11111111111111 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorks </td>
   <td style="text-align:right;"> 11 </td>
   <td style="text-align:right;"> 23,655 </td>
   <td style="text-align:left;"> 2150.45454545455 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> cycleSearch </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 53,400 </td>
   <td style="text-align:left;"> 5340 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 8,988 </td>
   <td style="text-align:right;font-weight: bold;"> 5,769,842 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  3 

<button class="btn btn-primary" data-toggle="collapse" data-target="#LUDVRCIMVP"> Show/Hide foundCycle query </button> 
 <div id="LUDVRCIMVP" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-06-04-43-xhtRRy--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  4 

<button class="btn btn-primary" data-toggle="collapse" data-target="#PTXQBWKHBL"> Show/Hide foundPath query </button> 
 <div id="PTXQBWKHBL" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-06-04-43-xhtRRy--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-06-05-48-vZUWy3--CV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 800 </td>
   <td style="text-align:left;width: 15em; "> 0.96 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 810 </td>
   <td style="text-align:right;width: 5em; "> 209,080 </td>
   <td style="text-align:right;width: 5em; "> 810 </td>
   <td style="text-align:right;width: 5em; "> 810 </td>
   <td style="text-align:right;width: 5em; "> 4,801 </td>
   <td style="text-align:right;width: 5em; "> 216,311 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 801 </td>
   <td style="text-align:right;width: 5em; "> 1,620 </td>
   <td style="text-align:right;width: 5em; "> 810 </td>
   <td style="text-align:right;width: 5em; "> 3,231 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-121.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-122.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-123.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> connectIfSimilar </td>
   <td style="text-align:right;"> 772,149 </td>
   <td style="text-align:right;"> 3,593,327 </td>
   <td style="text-align:left;"> 4.65367047033668 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> existsSimilarity </td>
   <td style="text-align:right;"> 772,149 </td>
   <td style="text-align:right;"> 3,574,796 </td>
   <td style="text-align:left;"> 4.6296712163067 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> similarityEdges </td>
   <td style="text-align:right;"> 772,149 </td>
   <td style="text-align:right;"> 3,504,040 </td>
   <td style="text-align:left;"> 4.53803605262715 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 803 </td>
   <td style="text-align:right;"> 39,633 </td>
   <td style="text-align:left;"> 49.3561643835616 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connectAllSimilar </td>
   <td style="text-align:right;"> 581 </td>
   <td style="text-align:right;"> 3,602,705 </td>
   <td style="text-align:left;"> 6200.86919104991 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connect </td>
   <td style="text-align:right;"> 127 </td>
   <td style="text-align:right;"> 324 </td>
   <td style="text-align:left;"> 2.5511811023622 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> cycleSearch </td>
   <td style="text-align:right;"> 25 </td>
   <td style="text-align:right;"> 456 </td>
   <td style="text-align:left;"> 18.24 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorks </td>
   <td style="text-align:right;"> 24 </td>
   <td style="text-align:right;"> 140 </td>
   <td style="text-align:left;"> 5.83333333333333 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 2,318,007 </td>
   <td style="text-align:right;font-weight: bold;"> 14,315,421 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  1 

<button class="btn btn-primary" data-toggle="collapse" data-target="#XPVIHGUYZP"> Show/Hide foundCycle query </button> 
 <div id="XPVIHGUYZP" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-06-05-48-vZUWy3--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  0 

<button class="btn btn-primary" data-toggle="collapse" data-target="#NNHWQPJNKP"> Show/Hide foundPath query </button> 
 <div id="NNHWQPJNKP" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-06-05-48-vZUWy3--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-06-05-48-vZUWy3--DV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 800 </td>
   <td style="text-align:left;width: 15em; "> 0.96 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 810 </td>
   <td style="text-align:right;width: 5em; "> 208,826 </td>
   <td style="text-align:right;width: 5em; "> 810 </td>
   <td style="text-align:right;width: 5em; "> 810 </td>
   <td style="text-align:right;width: 5em; "> 4,801 </td>
   <td style="text-align:right;width: 5em; "> 216,057 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 801 </td>
   <td style="text-align:right;width: 5em; "> 1,620 </td>
   <td style="text-align:right;width: 5em; "> 810 </td>
   <td style="text-align:right;width: 5em; "> 3,231 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-124.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-125.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-126.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> knowsAgent </td>
   <td style="text-align:right;"> 4,801 </td>
   <td style="text-align:right;"> 9,109 </td>
   <td style="text-align:left;"> 1.89731305977921 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> addItemToWork </td>
   <td style="text-align:right;"> 1,620 </td>
   <td style="text-align:right;"> 16,329 </td>
   <td style="text-align:left;"> 10.0796296296296 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> ownsWork </td>
   <td style="text-align:right;"> 810 </td>
   <td style="text-align:right;"> 8,289 </td>
   <td style="text-align:left;"> 10.2333333333333 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 803 </td>
   <td style="text-align:right;"> 38,419 </td>
   <td style="text-align:left;"> 47.8443337484433 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> searchAndConnect </td>
   <td style="text-align:right;"> 801 </td>
   <td style="text-align:right;"> 3,756,845 </td>
   <td style="text-align:left;"> 4690.19350811486 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> individualCycleSearch </td>
   <td style="text-align:right;"> 106 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 0.00943396226415094 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorks </td>
   <td style="text-align:right;"> 83 </td>
   <td style="text-align:right;"> 12,842 </td>
   <td style="text-align:left;"> 154.722891566265 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> cycleSearch </td>
   <td style="text-align:right;"> 82 </td>
   <td style="text-align:right;"> 26,558 </td>
   <td style="text-align:left;"> 323.878048780488 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 9,106 </td>
   <td style="text-align:right;font-weight: bold;"> 3,868,392 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  21 

<button class="btn btn-primary" data-toggle="collapse" data-target="#HDZUBOXBDB"> Show/Hide foundCycle query </button> 
 <div id="HDZUBOXBDB" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-06-05-48-vZUWy3--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  5 

<button class="btn btn-primary" data-toggle="collapse" data-target="#KPIWXQWCWO"> Show/Hide foundPath query </button> 
 <div id="KPIWXQWCWO" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-06-05-48-vZUWy3--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-06-06-51-hiGdwx--CV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 800 </td>
   <td style="text-align:left;width: 15em; "> 0.99 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 810 </td>
   <td style="text-align:right;width: 5em; "> 52,374 </td>
   <td style="text-align:right;width: 5em; "> 810 </td>
   <td style="text-align:right;width: 5em; "> 810 </td>
   <td style="text-align:right;width: 5em; "> 4,801 </td>
   <td style="text-align:right;width: 5em; "> 59,605 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 801 </td>
   <td style="text-align:right;width: 5em; "> 1,620 </td>
   <td style="text-align:right;width: 5em; "> 810 </td>
   <td style="text-align:right;width: 5em; "> 3,231 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-127.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-128.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-129.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> connectIfSimilar </td>
   <td style="text-align:right;"> 1,311,390 </td>
   <td style="text-align:right;"> 2,948,342 </td>
   <td style="text-align:left;"> 2.24825719274968 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> existsSimilarity </td>
   <td style="text-align:right;"> 1,311,390 </td>
   <td style="text-align:right;"> 2,925,280 </td>
   <td style="text-align:left;"> 2.23067127246662 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> similarityEdges </td>
   <td style="text-align:right;"> 1,311,390 </td>
   <td style="text-align:right;"> 2,874,485 </td>
   <td style="text-align:left;"> 2.19193756243375 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connectAllSimilar </td>
   <td style="text-align:right;"> 1,620 </td>
   <td style="text-align:right;"> 2,961,123 </td>
   <td style="text-align:left;"> 1827.8537037037 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 802 </td>
   <td style="text-align:right;"> 39,417 </td>
   <td style="text-align:left;"> 49.1483790523691 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connect </td>
   <td style="text-align:right;"> 62 </td>
   <td style="text-align:right;"> 163 </td>
   <td style="text-align:left;"> 2.62903225806452 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> cycleSearch </td>
   <td style="text-align:right;"> 54 </td>
   <td style="text-align:right;"> 1,561 </td>
   <td style="text-align:left;"> 28.9074074074074 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorks </td>
   <td style="text-align:right;"> 53 </td>
   <td style="text-align:right;"> 252 </td>
   <td style="text-align:left;"> 4.75471698113208 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> searchAndConnect </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 42,475 </td>
   <td style="text-align:left;"> 4247.5 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 3,936,771 </td>
   <td style="text-align:right;font-weight: bold;"> 11,793,098 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  1 

<button class="btn btn-primary" data-toggle="collapse" data-target="#MNZJCDFXXZ"> Show/Hide foundCycle query </button> 
 <div id="MNZJCDFXXZ" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-06-06-51-hiGdwx--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  0 

<button class="btn btn-primary" data-toggle="collapse" data-target="#MMQKHCIENM"> Show/Hide foundPath query </button> 
 <div id="MMQKHCIENM" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-06-06-51-hiGdwx--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-06-06-51-hiGdwx--DV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 800 </td>
   <td style="text-align:left;width: 15em; "> 0.99 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 810 </td>
   <td style="text-align:right;width: 5em; "> 52,250 </td>
   <td style="text-align:right;width: 5em; "> 810 </td>
   <td style="text-align:right;width: 5em; "> 810 </td>
   <td style="text-align:right;width: 5em; "> 4,801 </td>
   <td style="text-align:right;width: 5em; "> 59,481 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 801 </td>
   <td style="text-align:right;width: 5em; "> 1,620 </td>
   <td style="text-align:right;width: 5em; "> 810 </td>
   <td style="text-align:right;width: 5em; "> 3,231 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-130.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-131.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-132.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> knowsAgent </td>
   <td style="text-align:right;"> 4,801 </td>
   <td style="text-align:right;"> 9,131 </td>
   <td style="text-align:left;"> 1.90189543845032 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> addItemToWork </td>
   <td style="text-align:right;"> 1,620 </td>
   <td style="text-align:right;"> 14,867 </td>
   <td style="text-align:left;"> 9.17716049382716 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> ownsWork </td>
   <td style="text-align:right;"> 810 </td>
   <td style="text-align:right;"> 7,571 </td>
   <td style="text-align:left;"> 9.34691358024691 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 803 </td>
   <td style="text-align:right;"> 45,060 </td>
   <td style="text-align:left;"> 56.1145703611457 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> searchAndConnect </td>
   <td style="text-align:right;"> 791 </td>
   <td style="text-align:right;"> 2,627,747 </td>
   <td style="text-align:left;"> 3322.05689001264 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> individualCycleSearch </td>
   <td style="text-align:right;"> 64 </td>
   <td style="text-align:right;"> 2 </td>
   <td style="text-align:left;"> 0.03125 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorksItems </td>
   <td style="text-align:right;"> 18 </td>
   <td style="text-align:right;"> 28 </td>
   <td style="text-align:left;"> 1.55555555555556 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorks </td>
   <td style="text-align:right;"> 12 </td>
   <td style="text-align:right;"> 9,355 </td>
   <td style="text-align:left;"> 779.583333333333 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> cycleSearch </td>
   <td style="text-align:right;"> 11 </td>
   <td style="text-align:right;"> 28,497 </td>
   <td style="text-align:left;"> 2590.63636363636 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 8,930 </td>
   <td style="text-align:right;font-weight: bold;"> 2,742,258 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  3 

<button class="btn btn-primary" data-toggle="collapse" data-target="#IXJMPIGZYF"> Show/Hide foundCycle query </button> 
 <div id="IXJMPIGZYF" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-06-06-51-hiGdwx--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  6 

<button class="btn btn-primary" data-toggle="collapse" data-target="#LWVQEUGEQA"> Show/Hide foundPath query </button> 
 <div id="LWVQEUGEQA" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-06-06-51-hiGdwx--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
## Scatterplots of experiment 

<img src="index_files/figure-html/get-experiment-info-133.png" width="672" /><img src="index_files/figure-html/get-experiment-info-134.png" width="672" /><img src="index_files/figure-html/get-experiment-info-135.png" width="672" /><img src="index_files/figure-html/get-experiment-info-136.png" width="672" /><img src="index_files/figure-html/get-experiment-info-137.png" width="672" /><img src="index_files/figure-html/get-experiment-info-138.png" width="672" /><img src="index_files/figure-html/get-experiment-info-139.png" width="672" /><img src="index_files/figure-html/get-experiment-info-140.png" width="672" /><img src="index_files/figure-html/get-experiment-info-141.png" width="672" /><img src="index_files/figure-html/get-experiment-info-142.png" width="672" /><img src="index_files/figure-html/get-experiment-info-143.png" width="672" /><img src="index_files/figure-html/get-experiment-info-144.png" width="672" />   

# Experiment  3 

Same as above, agentNumbers=[700, 800], randomWorksNumberMultipliers=[2] 

## Experimental set up 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;">   </th>
   <th style="text-align:left;"> Values </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> experimentId </td>
   <td style="text-align:left;"> EXP09-07-02-54-hATxwM </td>
  </tr>
  <tr>
   <td style="text-align:left;"> agentNumbers </td>
   <td style="text-align:left;"> 700, 800 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> maxDistances </td>
   <td style="text-align:left;"> 10 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> randomWorksNumberMultipliers </td>
   <td style="text-align:left;"> 2 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> similarityConnectThresholds </td>
   <td style="text-align:left;"> 0.9, 0.93, 0.96, 0.99 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> chainLengths </td>
   <td style="text-align:left;"> 10 </td>
  </tr>
</tbody>
</table>
## Descriptive analysis of each simulation

###  SIM09-07-02-54-AmUqkP--CV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 700 </td>
   <td style="text-align:left;width: 15em; "> 0.9 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 2 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 1,410 </td>
   <td style="text-align:right;width: 5em; "> 1,542,412 </td>
   <td style="text-align:right;width: 5em; "> 1,410 </td>
   <td style="text-align:right;width: 5em; "> 1,410 </td>
   <td style="text-align:right;width: 5em; "> 4,201 </td>
   <td style="text-align:right;width: 5em; "> 1,550,843 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 701 </td>
   <td style="text-align:right;width: 5em; "> 2,820 </td>
   <td style="text-align:right;width: 5em; "> 1,410 </td>
   <td style="text-align:right;width: 5em; "> 4,931 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-145.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-146.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-147.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> connectIfSimilar </td>
   <td style="text-align:right;"> 268,787 </td>
   <td style="text-align:right;"> 3,602,162 </td>
   <td style="text-align:left;"> 13.4015484379825 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> existsSimilarity </td>
   <td style="text-align:right;"> 268,787 </td>
   <td style="text-align:right;"> 3,593,238 </td>
   <td style="text-align:left;"> 13.368347427517 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> similarityEdges </td>
   <td style="text-align:right;"> 268,787 </td>
   <td style="text-align:right;"> 3,498,343 </td>
   <td style="text-align:left;"> 13.0152983589236 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> cycleSearch </td>
   <td style="text-align:right;"> 836 </td>
   <td style="text-align:right;"> 34,170 </td>
   <td style="text-align:left;"> 40.8732057416268 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorks </td>
   <td style="text-align:right;"> 835 </td>
   <td style="text-align:right;"> 2,938 </td>
   <td style="text-align:left;"> 3.5185628742515 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 703 </td>
   <td style="text-align:right;"> 29,347 </td>
   <td style="text-align:left;"> 41.7453769559033 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connectAllSimilar </td>
   <td style="text-align:right;"> 97 </td>
   <td style="text-align:right;"> 3,605,499 </td>
   <td style="text-align:left;"> 37170.0927835052 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connect </td>
   <td style="text-align:right;"> 90 </td>
   <td style="text-align:right;"> 355 </td>
   <td style="text-align:left;"> 3.94444444444444 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 808,922 </td>
   <td style="text-align:right;font-weight: bold;"> 14,366,052 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  1 

<button class="btn btn-primary" data-toggle="collapse" data-target="#NTDSFTSVEZ"> Show/Hide foundCycle query </button> 
 <div id="NTDSFTSVEZ" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-07-02-54-AmUqkP--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  0 

<button class="btn btn-primary" data-toggle="collapse" data-target="#NPFPWPPDJA"> Show/Hide foundPath query </button> 
 <div id="NPFPWPPDJA" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-07-02-54-AmUqkP--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-07-02-54-AmUqkP--DV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 700 </td>
   <td style="text-align:left;width: 15em; "> 0.9 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 2 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 1,410 </td>
   <td style="text-align:right;width: 5em; "> 1,542,107 </td>
   <td style="text-align:right;width: 5em; "> 1,410 </td>
   <td style="text-align:right;width: 5em; "> 1,410 </td>
   <td style="text-align:right;width: 5em; "> 4,201 </td>
   <td style="text-align:right;width: 5em; "> 1,550,538 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 701 </td>
   <td style="text-align:right;width: 5em; "> 2,820 </td>
   <td style="text-align:right;width: 5em; "> 1,410 </td>
   <td style="text-align:right;width: 5em; "> 4,931 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-148.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-149.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-150.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> knowsAgent </td>
   <td style="text-align:right;"> 4,201 </td>
   <td style="text-align:right;"> 10,437 </td>
   <td style="text-align:left;"> 2.48440847417282 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> addItemToWork </td>
   <td style="text-align:right;"> 2,820 </td>
   <td style="text-align:right;"> 48,140 </td>
   <td style="text-align:left;"> 17.0709219858156 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> ownsWork </td>
   <td style="text-align:right;"> 1,410 </td>
   <td style="text-align:right;"> 23,686 </td>
   <td style="text-align:left;"> 16.7985815602837 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> individualCycleSearch </td>
   <td style="text-align:right;"> 1,004 </td>
   <td style="text-align:right;"> 56 </td>
   <td style="text-align:left;"> 0.0557768924302789 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 703 </td>
   <td style="text-align:right;"> 31,521 </td>
   <td style="text-align:left;"> 44.8378378378378 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> searchAndConnect </td>
   <td style="text-align:right;"> 701 </td>
   <td style="text-align:right;"> 33,347,436 </td>
   <td style="text-align:left;"> 47571.2353780314 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorks </td>
   <td style="text-align:right;"> 170 </td>
   <td style="text-align:right;"> 90,267 </td>
   <td style="text-align:left;"> 530.982352941176 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> cycleSearch </td>
   <td style="text-align:right;"> 169 </td>
   <td style="text-align:right;"> 229,319 </td>
   <td style="text-align:left;"> 1356.91715976331 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 11,178 </td>
   <td style="text-align:right;font-weight: bold;"> 33,780,862 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  129 

<button class="btn btn-primary" data-toggle="collapse" data-target="#PSJARBHJPP"> Show/Hide foundCycle query </button> 
 <div id="PSJARBHJPP" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-07-02-54-AmUqkP--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  4 

<button class="btn btn-primary" data-toggle="collapse" data-target="#JZKPBTCSUU"> Show/Hide foundPath query </button> 
 <div id="JZKPBTCSUU" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-07-02-54-AmUqkP--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-07-04-13-Tqhuy8--CV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 700 </td>
   <td style="text-align:left;width: 15em; "> 0.93 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 2 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 1,410 </td>
   <td style="text-align:right;width: 5em; "> 1,084,042 </td>
   <td style="text-align:right;width: 5em; "> 1,410 </td>
   <td style="text-align:right;width: 5em; "> 1,410 </td>
   <td style="text-align:right;width: 5em; "> 4,201 </td>
   <td style="text-align:right;width: 5em; "> 1,092,473 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 701 </td>
   <td style="text-align:right;width: 5em; "> 2,820 </td>
   <td style="text-align:right;width: 5em; "> 1,410 </td>
   <td style="text-align:right;width: 5em; "> 4,931 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-151.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-152.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-153.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> existsSimilarity </td>
   <td style="text-align:right;"> 358,337 </td>
   <td style="text-align:right;"> 3,602,416 </td>
   <td style="text-align:left;"> 10.0531510840354 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connectIfSimilar </td>
   <td style="text-align:right;"> 358,313 </td>
   <td style="text-align:right;"> 3,612,902 </td>
   <td style="text-align:left;"> 10.0830893660012 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> similarityEdges </td>
   <td style="text-align:right;"> 358,297 </td>
   <td style="text-align:right;"> 3,517,538 </td>
   <td style="text-align:left;"> 9.81738055300491 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 703 </td>
   <td style="text-align:right;"> 30,337 </td>
   <td style="text-align:left;"> 43.1536273115221 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> cycleSearch </td>
   <td style="text-align:right;"> 427 </td>
   <td style="text-align:right;"> 13,399 </td>
   <td style="text-align:left;"> 31.3793911007026 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorks </td>
   <td style="text-align:right;"> 426 </td>
   <td style="text-align:right;"> 1,665 </td>
   <td style="text-align:left;"> 3.90845070422535 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connectAllSimilar </td>
   <td style="text-align:right;"> 131 </td>
   <td style="text-align:right;"> 3,641,793 </td>
   <td style="text-align:left;"> 27799.9465648855 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connect </td>
   <td style="text-align:right;"> 85 </td>
   <td style="text-align:right;"> 261 </td>
   <td style="text-align:left;"> 3.07058823529412 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 1,076,719 </td>
   <td style="text-align:right;font-weight: bold;"> 14,420,311 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  1 

<button class="btn btn-primary" data-toggle="collapse" data-target="#VVVFTEDGNJ"> Show/Hide foundCycle query </button> 
 <div id="VVVFTEDGNJ" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-07-04-13-Tqhuy8--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  0 

<button class="btn btn-primary" data-toggle="collapse" data-target="#VVBSDXEFFH"> Show/Hide foundPath query </button> 
 <div id="VVBSDXEFFH" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-07-04-13-Tqhuy8--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-07-04-13-Tqhuy8--DV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 700 </td>
   <td style="text-align:left;width: 15em; "> 0.93 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 2 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 1,410 </td>
   <td style="text-align:right;width: 5em; "> 1,083,862 </td>
   <td style="text-align:right;width: 5em; "> 1,410 </td>
   <td style="text-align:right;width: 5em; "> 1,410 </td>
   <td style="text-align:right;width: 5em; "> 4,201 </td>
   <td style="text-align:right;width: 5em; "> 1,092,293 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 701 </td>
   <td style="text-align:right;width: 5em; "> 2,820 </td>
   <td style="text-align:right;width: 5em; "> 1,410 </td>
   <td style="text-align:right;width: 5em; "> 4,931 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-154.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-155.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-156.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> knowsAgent </td>
   <td style="text-align:right;"> 4,201 </td>
   <td style="text-align:right;"> 8,008 </td>
   <td style="text-align:left;"> 1.90621280647465 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> addItemToWork </td>
   <td style="text-align:right;"> 2,820 </td>
   <td style="text-align:right;"> 33,878 </td>
   <td style="text-align:left;"> 12.013475177305 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> ownsWork </td>
   <td style="text-align:right;"> 1,410 </td>
   <td style="text-align:right;"> 16,951 </td>
   <td style="text-align:left;"> 12.0219858156028 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 703 </td>
   <td style="text-align:right;"> 30,397 </td>
   <td style="text-align:left;"> 43.2389758179232 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> searchAndConnect </td>
   <td style="text-align:right;"> 701 </td>
   <td style="text-align:right;"> 20,946,601 </td>
   <td style="text-align:left;"> 29881.0285306705 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> individualCycleSearch </td>
   <td style="text-align:right;"> 571 </td>
   <td style="text-align:right;"> 13 </td>
   <td style="text-align:left;"> 0.0227670753064799 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorks </td>
   <td style="text-align:right;"> 146 </td>
   <td style="text-align:right;"> 27,449 </td>
   <td style="text-align:left;"> 188.006849315069 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> cycleSearch </td>
   <td style="text-align:right;"> 145 </td>
   <td style="text-align:right;"> 53,548 </td>
   <td style="text-align:left;"> 369.296551724138 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 10,697 </td>
   <td style="text-align:right;font-weight: bold;"> 21,116,845 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  109 

<button class="btn btn-primary" data-toggle="collapse" data-target="#VTDIXNFEMP"> Show/Hide foundCycle query </button> 
 <div id="VTDIXNFEMP" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-07-04-13-Tqhuy8--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  0 

<button class="btn btn-primary" data-toggle="collapse" data-target="#FXRHIFQSXJ"> Show/Hide foundPath query </button> 
 <div id="FXRHIFQSXJ" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-07-04-13-Tqhuy8--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-07-05-25-o9GkLq--CV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 700 </td>
   <td style="text-align:left;width: 15em; "> 0.96 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 2 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 1,410 </td>
   <td style="text-align:right;width: 5em; "> 634,532 </td>
   <td style="text-align:right;width: 5em; "> 1,410 </td>
   <td style="text-align:right;width: 5em; "> 1,410 </td>
   <td style="text-align:right;width: 5em; "> 4,201 </td>
   <td style="text-align:right;width: 5em; "> 642,963 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 701 </td>
   <td style="text-align:right;width: 5em; "> 2,820 </td>
   <td style="text-align:right;width: 5em; "> 1,410 </td>
   <td style="text-align:right;width: 5em; "> 4,931 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-157.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-158.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-159.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> connectIfSimilar </td>
   <td style="text-align:right;"> 475,455 </td>
   <td style="text-align:right;"> 3,602,688 </td>
   <td style="text-align:left;"> 7.57734801400763 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> existsSimilarity </td>
   <td style="text-align:right;"> 475,455 </td>
   <td style="text-align:right;"> 3,589,801 </td>
   <td style="text-align:left;"> 7.55024345101009 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> similarityEdges </td>
   <td style="text-align:right;"> 475,455 </td>
   <td style="text-align:right;"> 3,520,844 </td>
   <td style="text-align:left;"> 7.4052097464534 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 703 </td>
   <td style="text-align:right;"> 33,415 </td>
   <td style="text-align:left;"> 47.5320056899004 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> cycleSearch </td>
   <td style="text-align:right;"> 199 </td>
   <td style="text-align:right;"> 6,400 </td>
   <td style="text-align:left;"> 32.1608040201005 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorks </td>
   <td style="text-align:right;"> 198 </td>
   <td style="text-align:right;"> 516 </td>
   <td style="text-align:left;"> 2.60606060606061 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connectAllSimilar </td>
   <td style="text-align:right;"> 174 </td>
   <td style="text-align:right;"> 3,608,665 </td>
   <td style="text-align:left;"> 20739.4540229885 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connect </td>
   <td style="text-align:right;"> 64 </td>
   <td style="text-align:right;"> 214 </td>
   <td style="text-align:left;"> 3.34375 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 1,427,703 </td>
   <td style="text-align:right;font-weight: bold;"> 14,362,543 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  1 

<button class="btn btn-primary" data-toggle="collapse" data-target="#HHBLSLLRAT"> Show/Hide foundCycle query </button> 
 <div id="HHBLSLLRAT" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-07-05-25-o9GkLq--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  0 

<button class="btn btn-primary" data-toggle="collapse" data-target="#KVGMBVMQME"> Show/Hide foundPath query </button> 
 <div id="KVGMBVMQME" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-07-05-25-o9GkLq--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-07-05-25-o9GkLq--DV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 700 </td>
   <td style="text-align:left;width: 15em; "> 0.96 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 2 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 1,410 </td>
   <td style="text-align:right;width: 5em; "> 634,404 </td>
   <td style="text-align:right;width: 5em; "> 1,410 </td>
   <td style="text-align:right;width: 5em; "> 1,410 </td>
   <td style="text-align:right;width: 5em; "> 4,201 </td>
   <td style="text-align:right;width: 5em; "> 642,835 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 701 </td>
   <td style="text-align:right;width: 5em; "> 2,820 </td>
   <td style="text-align:right;width: 5em; "> 1,410 </td>
   <td style="text-align:right;width: 5em; "> 4,931 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-160.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-161.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-162.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> knowsAgent </td>
   <td style="text-align:right;"> 4,201 </td>
   <td style="text-align:right;"> 8,050 </td>
   <td style="text-align:left;"> 1.91621042608903 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> addItemToWork </td>
   <td style="text-align:right;"> 2,820 </td>
   <td style="text-align:right;"> 26,146 </td>
   <td style="text-align:left;"> 9.27163120567376 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> ownsWork </td>
   <td style="text-align:right;"> 1,410 </td>
   <td style="text-align:right;"> 12,289 </td>
   <td style="text-align:left;"> 8.71560283687943 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 703 </td>
   <td style="text-align:right;"> 31,416 </td>
   <td style="text-align:left;"> 44.6884779516358 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> searchAndConnect </td>
   <td style="text-align:right;"> 701 </td>
   <td style="text-align:right;"> 10,074,989 </td>
   <td style="text-align:left;"> 14372.3095577746 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> individualCycleSearch </td>
   <td style="text-align:right;"> 277 </td>
   <td style="text-align:right;"> 9 </td>
   <td style="text-align:left;"> 0.0324909747292419 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorks </td>
   <td style="text-align:right;"> 80 </td>
   <td style="text-align:right;"> 29,641 </td>
   <td style="text-align:left;"> 370.5125 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> cycleSearch </td>
   <td style="text-align:right;"> 79 </td>
   <td style="text-align:right;"> 95,410 </td>
   <td style="text-align:left;"> 1207.72151898734 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 10,271 </td>
   <td style="text-align:right;font-weight: bold;"> 10,277,950 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  26 

<button class="btn btn-primary" data-toggle="collapse" data-target="#ZKFHUWKJOS"> Show/Hide foundCycle query </button> 
 <div id="ZKFHUWKJOS" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-07-05-25-o9GkLq--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  5 

<button class="btn btn-primary" data-toggle="collapse" data-target="#WGLPPFDYGQ"> Show/Hide foundPath query </button> 
 <div id="WGLPPFDYGQ" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-07-05-25-o9GkLq--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-07-06-32-2MR3Ho--CV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 700 </td>
   <td style="text-align:left;width: 15em; "> 0.99 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 2 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 1,410 </td>
   <td style="text-align:right;width: 5em; "> 159,898 </td>
   <td style="text-align:right;width: 5em; "> 1,410 </td>
   <td style="text-align:right;width: 5em; "> 1,410 </td>
   <td style="text-align:right;width: 5em; "> 4,201 </td>
   <td style="text-align:right;width: 5em; "> 168,329 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 701 </td>
   <td style="text-align:right;width: 5em; "> 2,820 </td>
   <td style="text-align:right;width: 5em; "> 1,410 </td>
   <td style="text-align:right;width: 5em; "> 4,931 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-163.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-164.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-165.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;">   </th>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> 1 </td>
   <td style="text-align:left;"> connectIfSimilar </td>
   <td style="text-align:right;"> 1,205,309 </td>
   <td style="text-align:right;"> 3,587,387 </td>
   <td style="text-align:left;"> 2.97632142463053 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> 2 </td>
   <td style="text-align:left;"> existsSimilarity </td>
   <td style="text-align:right;"> 1,205,309 </td>
   <td style="text-align:right;"> 3,564,241 </td>
   <td style="text-align:left;"> 2.95711805022612 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> 3 </td>
   <td style="text-align:left;"> similarityEdges </td>
   <td style="text-align:right;"> 1,205,309 </td>
   <td style="text-align:right;"> 3,502,307 </td>
   <td style="text-align:left;"> 2.90573371641629 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> 4 </td>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 703 </td>
   <td style="text-align:right;"> 32,332 </td>
   <td style="text-align:left;"> 45.9914651493599 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> 5 </td>
   <td style="text-align:left;"> connectAllSimilar </td>
   <td style="text-align:right;"> 466 </td>
   <td style="text-align:right;"> 3,600,115 </td>
   <td style="text-align:left;"> 7725.5686695279 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> 6 </td>
   <td style="text-align:left;"> cycleSearch </td>
   <td style="text-align:right;"> 107 </td>
   <td style="text-align:right;"> 2,596 </td>
   <td style="text-align:left;"> 24.2616822429907 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> 7 </td>
   <td style="text-align:left;"> getWorks </td>
   <td style="text-align:right;"> 106 </td>
   <td style="text-align:right;"> 315 </td>
   <td style="text-align:left;"> 2.97169811320755 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> 8 </td>
   <td style="text-align:left;"> connect </td>
   <td style="text-align:right;"> 48 </td>
   <td style="text-align:right;"> 116 </td>
   <td style="text-align:left;"> 2.41666666666667 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:left;"> searchAndConnect </td>
   <td style="text-align:right;"> 3 </td>
   <td style="text-align:right;"> 30,822 </td>
   <td style="text-align:left;"> 10274 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> 11 </td>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 3,617,360 </td>
   <td style="text-align:right;font-weight: bold;"> 14,320,231 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  1 

<button class="btn btn-primary" data-toggle="collapse" data-target="#YTKHEFMEMX"> Show/Hide foundCycle query </button> 
 <div id="YTKHEFMEMX" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-07-06-32-2MR3Ho--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  0 

<button class="btn btn-primary" data-toggle="collapse" data-target="#LWHBDOOOXK"> Show/Hide foundPath query </button> 
 <div id="LWHBDOOOXK" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-07-06-32-2MR3Ho--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-07-06-32-2MR3Ho--DV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 700 </td>
   <td style="text-align:left;width: 15em; "> 0.99 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 2 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 1,410 </td>
   <td style="text-align:right;width: 5em; "> 159,802 </td>
   <td style="text-align:right;width: 5em; "> 1,410 </td>
   <td style="text-align:right;width: 5em; "> 1,410 </td>
   <td style="text-align:right;width: 5em; "> 4,201 </td>
   <td style="text-align:right;width: 5em; "> 168,233 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 701 </td>
   <td style="text-align:right;width: 5em; "> 2,820 </td>
   <td style="text-align:right;width: 5em; "> 1,410 </td>
   <td style="text-align:right;width: 5em; "> 4,931 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-166.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-167.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-168.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> knowsAgent </td>
   <td style="text-align:right;"> 4,201 </td>
   <td style="text-align:right;"> 7,925 </td>
   <td style="text-align:left;"> 1.88645560580814 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> addItemToWork </td>
   <td style="text-align:right;"> 2,820 </td>
   <td style="text-align:right;"> 37,604 </td>
   <td style="text-align:left;"> 13.3347517730496 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> ownsWork </td>
   <td style="text-align:right;"> 1,410 </td>
   <td style="text-align:right;"> 19,532 </td>
   <td style="text-align:left;"> 13.8524822695035 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 703 </td>
   <td style="text-align:right;"> 32,654 </td>
   <td style="text-align:left;"> 46.4495021337127 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> searchAndConnect </td>
   <td style="text-align:right;"> 698 </td>
   <td style="text-align:right;"> 5,565,417 </td>
   <td style="text-align:left;"> 7973.37679083095 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> individualCycleSearch </td>
   <td style="text-align:right;"> 148 </td>
   <td style="text-align:right;"> 6 </td>
   <td style="text-align:left;"> 0.0405405405405405 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorks </td>
   <td style="text-align:right;"> 43 </td>
   <td style="text-align:right;"> 12,574 </td>
   <td style="text-align:left;"> 292.418604651163 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> cycleSearch </td>
   <td style="text-align:right;"> 42 </td>
   <td style="text-align:right;"> 30,501 </td>
   <td style="text-align:left;"> 726.214285714286 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorksItems </td>
   <td style="text-align:right;"> 18 </td>
   <td style="text-align:right;"> 29 </td>
   <td style="text-align:left;"> 1.61111111111111 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 10,083 </td>
   <td style="text-align:right;font-weight: bold;"> 5,706,242 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  3 

<button class="btn btn-primary" data-toggle="collapse" data-target="#WCWKQDMVIO"> Show/Hide foundCycle query </button> 
 <div id="WCWKQDMVIO" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-07-06-32-2MR3Ho--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  2 

<button class="btn btn-primary" data-toggle="collapse" data-target="#JELXGMXTNR"> Show/Hide foundPath query </button> 
 <div id="JELXGMXTNR" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-07-06-32-2MR3Ho--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-07-07-36-mFck8t--CV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 800 </td>
   <td style="text-align:left;width: 15em; "> 0.9 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 2 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 1,610 </td>
   <td style="text-align:right;width: 5em; "> 2,010,342 </td>
   <td style="text-align:right;width: 5em; "> 1,610 </td>
   <td style="text-align:right;width: 5em; "> 1,610 </td>
   <td style="text-align:right;width: 5em; "> 4,801 </td>
   <td style="text-align:right;width: 5em; "> 2,019,973 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 801 </td>
   <td style="text-align:right;width: 5em; "> 3,220 </td>
   <td style="text-align:right;width: 5em; "> 1,610 </td>
   <td style="text-align:right;width: 5em; "> 5,631 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-169.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-170.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-171.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> connectIfSimilar </td>
   <td style="text-align:right;"> 222,915 </td>
   <td style="text-align:right;"> 3,618,647 </td>
   <td style="text-align:left;"> 16.2333041742368 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> existsSimilarity </td>
   <td style="text-align:right;"> 222,915 </td>
   <td style="text-align:right;"> 3,611,057 </td>
   <td style="text-align:left;"> 16.1992553215351 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> similarityEdges </td>
   <td style="text-align:right;"> 222,915 </td>
   <td style="text-align:right;"> 3,513,485 </td>
   <td style="text-align:left;"> 15.7615458807169 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> cycleSearch </td>
   <td style="text-align:right;"> 1,252 </td>
   <td style="text-align:right;"> 49,432 </td>
   <td style="text-align:left;"> 39.482428115016 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorks </td>
   <td style="text-align:right;"> 1,251 </td>
   <td style="text-align:right;"> 5,110 </td>
   <td style="text-align:left;"> 4.08473221422862 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 803 </td>
   <td style="text-align:right;"> 41,074 </td>
   <td style="text-align:left;"> 51.1506849315069 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connect </td>
   <td style="text-align:right;"> 76 </td>
   <td style="text-align:right;"> 290 </td>
   <td style="text-align:left;"> 3.81578947368421 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connectAllSimilar </td>
   <td style="text-align:right;"> 70 </td>
   <td style="text-align:right;"> 3,621,589 </td>
   <td style="text-align:left;"> 51736.9857142857 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 672,197 </td>
   <td style="text-align:right;font-weight: bold;"> 14,460,684 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  1 

<button class="btn btn-primary" data-toggle="collapse" data-target="#QWTIYIIIQI"> Show/Hide foundCycle query </button> 
 <div id="QWTIYIIIQI" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-07-07-36-mFck8t--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  0 

<button class="btn btn-primary" data-toggle="collapse" data-target="#LYQXTSFWVI"> Show/Hide foundPath query </button> 
 <div id="LYQXTSFWVI" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-07-07-36-mFck8t--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-07-07-36-mFck8t--DV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 800 </td>
   <td style="text-align:left;width: 15em; "> 0.9 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 2 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 1,610 </td>
   <td style="text-align:right;width: 5em; "> 2,010,169 </td>
   <td style="text-align:right;width: 5em; "> 1,610 </td>
   <td style="text-align:right;width: 5em; "> 1,610 </td>
   <td style="text-align:right;width: 5em; "> 4,801 </td>
   <td style="text-align:right;width: 5em; "> 2,019,800 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 801 </td>
   <td style="text-align:right;width: 5em; "> 3,220 </td>
   <td style="text-align:right;width: 5em; "> 1,610 </td>
   <td style="text-align:right;width: 5em; "> 5,631 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-172.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-173.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-174.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> knowsAgent </td>
   <td style="text-align:right;"> 4,801 </td>
   <td style="text-align:right;"> 9,703 </td>
   <td style="text-align:left;"> 2.02103728389919 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> addItemToWork </td>
   <td style="text-align:right;"> 3,220 </td>
   <td style="text-align:right;"> 37,024 </td>
   <td style="text-align:left;"> 11.4981366459627 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> ownsWork </td>
   <td style="text-align:right;"> 1,610 </td>
   <td style="text-align:right;"> 19,134 </td>
   <td style="text-align:left;"> 11.8844720496894 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> individualCycleSearch </td>
   <td style="text-align:right;"> 1,403 </td>
   <td style="text-align:right;"> 45 </td>
   <td style="text-align:left;"> 0.0320741268709907 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 803 </td>
   <td style="text-align:right;"> 36,374 </td>
   <td style="text-align:left;"> 45.2976338729763 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> searchAndConnect </td>
   <td style="text-align:right;"> 801 </td>
   <td style="text-align:right;"> 53,165,673 </td>
   <td style="text-align:left;"> 66374.1235955056 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorks </td>
   <td style="text-align:right;"> 153 </td>
   <td style="text-align:right;"> 69,038 </td>
   <td style="text-align:left;"> 451.228758169935 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> cycleSearch </td>
   <td style="text-align:right;"> 152 </td>
   <td style="text-align:right;"> 134,427 </td>
   <td style="text-align:left;"> 884.388157894737 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 12,943 </td>
   <td style="text-align:right;font-weight: bold;"> 53,471,418 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  126 

<button class="btn btn-primary" data-toggle="collapse" data-target="#HRHOQIBJQI"> Show/Hide foundCycle query </button> 
 <div id="HRHOQIBJQI" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-07-07-36-mFck8t--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  0 

<button class="btn btn-primary" data-toggle="collapse" data-target="#IEHMYDUDII"> Show/Hide foundPath query </button> 
 <div id="IEHMYDUDII" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-07-07-36-mFck8t--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-07-09-04-3czu4g--CV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 800 </td>
   <td style="text-align:left;width: 15em; "> 0.93 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 2 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 1,610 </td>
   <td style="text-align:right;width: 5em; "> 1,424,774 </td>
   <td style="text-align:right;width: 5em; "> 1,610 </td>
   <td style="text-align:right;width: 5em; "> 1,610 </td>
   <td style="text-align:right;width: 5em; "> 4,801 </td>
   <td style="text-align:right;width: 5em; "> 1,434,405 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 801 </td>
   <td style="text-align:right;width: 5em; "> 3,220 </td>
   <td style="text-align:right;width: 5em; "> 1,610 </td>
   <td style="text-align:right;width: 5em; "> 5,631 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-175.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-176.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-177.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> connectIfSimilar </td>
   <td style="text-align:right;"> 307,587 </td>
   <td style="text-align:right;"> 3,613,144 </td>
   <td style="text-align:left;"> 11.7467383211904 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> existsSimilarity </td>
   <td style="text-align:right;"> 307,587 </td>
   <td style="text-align:right;"> 3,603,761 </td>
   <td style="text-align:left;"> 11.716233130789 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> similarityEdges </td>
   <td style="text-align:right;"> 307,587 </td>
   <td style="text-align:right;"> 3,512,505 </td>
   <td style="text-align:left;"> 11.4195495908475 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 803 </td>
   <td style="text-align:right;"> 155,021 </td>
   <td style="text-align:left;"> 193.052303860523 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> cycleSearch </td>
   <td style="text-align:right;"> 621 </td>
   <td style="text-align:right;"> 33,989 </td>
   <td style="text-align:left;"> 54.7326892109501 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorks </td>
   <td style="text-align:right;"> 621 </td>
   <td style="text-align:right;"> 3,664 </td>
   <td style="text-align:left;"> 5.90016103059581 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connectAllSimilar </td>
   <td style="text-align:right;"> 97 </td>
   <td style="text-align:right;"> 3,617,079 </td>
   <td style="text-align:left;"> 37289.4742268041 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connect </td>
   <td style="text-align:right;"> 59 </td>
   <td style="text-align:right;"> 280 </td>
   <td style="text-align:left;"> 4.74576271186441 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> searchAndConnect </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 527,977 </td>
   <td style="text-align:left;"> 52797.7 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 924,972 </td>
   <td style="text-align:right;font-weight: bold;"> 15,067,420 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  1 

<button class="btn btn-primary" data-toggle="collapse" data-target="#VXFYNCDFWM"> Show/Hide foundCycle query </button> 
 <div id="VXFYNCDFWM" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-07-09-04-3czu4g--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  0 

<button class="btn btn-primary" data-toggle="collapse" data-target="#SLEEHZLWIO"> Show/Hide foundPath query </button> 
 <div id="SLEEHZLWIO" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-07-09-04-3czu4g--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-07-09-04-3czu4g--DV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 800 </td>
   <td style="text-align:left;width: 15em; "> 0.93 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 2 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 1,610 </td>
   <td style="text-align:right;width: 5em; "> 1,424,656 </td>
   <td style="text-align:right;width: 5em; "> 1,610 </td>
   <td style="text-align:right;width: 5em; "> 1,610 </td>
   <td style="text-align:right;width: 5em; "> 4,801 </td>
   <td style="text-align:right;width: 5em; "> 1,434,287 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 801 </td>
   <td style="text-align:right;width: 5em; "> 3,220 </td>
   <td style="text-align:right;width: 5em; "> 1,610 </td>
   <td style="text-align:right;width: 5em; "> 5,631 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-178.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-179.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-180.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> knowsAgent </td>
   <td style="text-align:right;"> 4,801 </td>
   <td style="text-align:right;"> 9,630 </td>
   <td style="text-align:left;"> 2.00583211830869 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> addItemToWork </td>
   <td style="text-align:right;"> 3,220 </td>
   <td style="text-align:right;"> 33,293 </td>
   <td style="text-align:left;"> 10.3394409937888 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> ownsWork </td>
   <td style="text-align:right;"> 1,610 </td>
   <td style="text-align:right;"> 16,437 </td>
   <td style="text-align:left;"> 10.2093167701863 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 803 </td>
   <td style="text-align:right;"> 145,040 </td>
   <td style="text-align:left;"> 180.622665006227 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> searchAndConnect </td>
   <td style="text-align:right;"> 791 </td>
   <td style="text-align:right;"> 29,566,221 </td>
   <td style="text-align:left;"> 37378.2819216182 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> individualCycleSearch </td>
   <td style="text-align:right;"> 627 </td>
   <td style="text-align:right;"> 12 </td>
   <td style="text-align:left;"> 0.0191387559808612 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorksItems </td>
   <td style="text-align:right;"> 18 </td>
   <td style="text-align:right;"> 36 </td>
   <td style="text-align:left;"> 2 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> cycleSearch </td>
   <td style="text-align:right;"> 7 </td>
   <td style="text-align:right;"> 211,942 </td>
   <td style="text-align:left;"> 30277.4285714286 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorks </td>
   <td style="text-align:right;"> 7 </td>
   <td style="text-align:right;"> 124,113 </td>
   <td style="text-align:left;"> 17730.4285714286 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 11,884 </td>
   <td style="text-align:right;font-weight: bold;"> 30,106,724 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  3 

<button class="btn btn-primary" data-toggle="collapse" data-target="#RUDEHCUQMN"> Show/Hide foundCycle query </button> 
 <div id="RUDEHCUQMN" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-07-09-04-3czu4g--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  1 

<button class="btn btn-primary" data-toggle="collapse" data-target="#BFNHGKTFIE"> Show/Hide foundPath query </button> 
 <div id="BFNHGKTFIE" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-07-09-04-3czu4g--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-07-10-22-fh4Yzs--CV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 800 </td>
   <td style="text-align:left;width: 15em; "> 0.96 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 2 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 1,610 </td>
   <td style="text-align:right;width: 5em; "> 821,174 </td>
   <td style="text-align:right;width: 5em; "> 1,610 </td>
   <td style="text-align:right;width: 5em; "> 1,610 </td>
   <td style="text-align:right;width: 5em; "> 4,801 </td>
   <td style="text-align:right;width: 5em; "> 830,805 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 801 </td>
   <td style="text-align:right;width: 5em; "> 3,220 </td>
   <td style="text-align:right;width: 5em; "> 1,610 </td>
   <td style="text-align:right;width: 5em; "> 5,631 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-181.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-182.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-183.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> connectIfSimilar </td>
   <td style="text-align:right;"> 456,315 </td>
   <td style="text-align:right;"> 3,596,960 </td>
   <td style="text-align:left;"> 7.88262494110428 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> existsSimilarity </td>
   <td style="text-align:right;"> 456,315 </td>
   <td style="text-align:right;"> 3,584,537 </td>
   <td style="text-align:left;"> 7.85540032652882 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> similarityEdges </td>
   <td style="text-align:right;"> 456,315 </td>
   <td style="text-align:right;"> 3,509,335 </td>
   <td style="text-align:left;"> 7.69059750391725 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 803 </td>
   <td style="text-align:right;"> 40,085 </td>
   <td style="text-align:left;"> 49.9190535491905 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> cycleSearch </td>
   <td style="text-align:right;"> 304 </td>
   <td style="text-align:right;"> 9,529 </td>
   <td style="text-align:left;"> 31.3453947368421 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorks </td>
   <td style="text-align:right;"> 303 </td>
   <td style="text-align:right;"> 869 </td>
   <td style="text-align:left;"> 2.86798679867987 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connectAllSimilar </td>
   <td style="text-align:right;"> 145 </td>
   <td style="text-align:right;"> 3,602,480 </td>
   <td style="text-align:left;"> 24844.6896551724 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connect </td>
   <td style="text-align:right;"> 46 </td>
   <td style="text-align:right;"> 258 </td>
   <td style="text-align:left;"> 5.60869565217391 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 1,370,546 </td>
   <td style="text-align:right;font-weight: bold;"> 14,344,053 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  1 

<button class="btn btn-primary" data-toggle="collapse" data-target="#RGOCMWSGFN"> Show/Hide foundCycle query </button> 
 <div id="RGOCMWSGFN" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-07-10-22-fh4Yzs--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  0 

<button class="btn btn-primary" data-toggle="collapse" data-target="#RXYJDWFLWQ"> Show/Hide foundPath query </button> 
 <div id="RXYJDWFLWQ" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-07-10-22-fh4Yzs--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-07-10-22-fh4Yzs--DV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 800 </td>
   <td style="text-align:left;width: 15em; "> 0.96 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 2 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 1,610 </td>
   <td style="text-align:right;width: 5em; "> 821,082 </td>
   <td style="text-align:right;width: 5em; "> 1,610 </td>
   <td style="text-align:right;width: 5em; "> 1,610 </td>
   <td style="text-align:right;width: 5em; "> 4,801 </td>
   <td style="text-align:right;width: 5em; "> 830,713 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 801 </td>
   <td style="text-align:right;width: 5em; "> 3,220 </td>
   <td style="text-align:right;width: 5em; "> 1,610 </td>
   <td style="text-align:right;width: 5em; "> 5,631 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-184.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-185.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-186.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> knowsAgent </td>
   <td style="text-align:right;"> 4,801 </td>
   <td style="text-align:right;"> 9,618 </td>
   <td style="text-align:left;"> 2.00333263903353 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> addItemToWork </td>
   <td style="text-align:right;"> 3,220 </td>
   <td style="text-align:right;"> 32,555 </td>
   <td style="text-align:left;"> 10.110248447205 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> ownsWork </td>
   <td style="text-align:right;"> 1,610 </td>
   <td style="text-align:right;"> 15,654 </td>
   <td style="text-align:left;"> 9.72298136645963 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 803 </td>
   <td style="text-align:right;"> 39,020 </td>
   <td style="text-align:left;"> 48.5927770859278 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> searchAndConnect </td>
   <td style="text-align:right;"> 801 </td>
   <td style="text-align:right;"> 15,186,014 </td>
   <td style="text-align:left;"> 18958.8189762797 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> individualCycleSearch </td>
   <td style="text-align:right;"> 448 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:left;"> 0.0223214285714286 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorks </td>
   <td style="text-align:right;"> 146 </td>
   <td style="text-align:right;"> 26,620 </td>
   <td style="text-align:left;"> 182.328767123288 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> cycleSearch </td>
   <td style="text-align:right;"> 145 </td>
   <td style="text-align:right;"> 90,175 </td>
   <td style="text-align:left;"> 621.896551724138 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 11,974 </td>
   <td style="text-align:right;font-weight: bold;"> 15,399,666 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  99 

<button class="btn btn-primary" data-toggle="collapse" data-target="#DSADLRAPPQ"> Show/Hide foundCycle query </button> 
 <div id="DSADLRAPPQ" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-07-10-22-fh4Yzs--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  4 

<button class="btn btn-primary" data-toggle="collapse" data-target="#MRMSUPFMNA"> Show/Hide foundPath query </button> 
 <div id="MRMSUPFMNA" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-07-10-22-fh4Yzs--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-07-11-31-vI2Zz9--CV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 800 </td>
   <td style="text-align:left;width: 15em; "> 0.99 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 2 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 1,610 </td>
   <td style="text-align:right;width: 5em; "> 208,276 </td>
   <td style="text-align:right;width: 5em; "> 1,610 </td>
   <td style="text-align:right;width: 5em; "> 1,610 </td>
   <td style="text-align:right;width: 5em; "> 4,801 </td>
   <td style="text-align:right;width: 5em; "> 217,907 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 801 </td>
   <td style="text-align:right;width: 5em; "> 3,220 </td>
   <td style="text-align:right;width: 5em; "> 1,610 </td>
   <td style="text-align:right;width: 5em; "> 5,631 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-187.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-188.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-189.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> connectIfSimilar </td>
   <td style="text-align:right;"> 1,097,079 </td>
   <td style="text-align:right;"> 3,597,196 </td>
   <td style="text-align:left;"> 3.27888511219338 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> existsSimilarity </td>
   <td style="text-align:right;"> 1,097,079 </td>
   <td style="text-align:right;"> 3,575,023 </td>
   <td style="text-align:left;"> 3.2586741702284 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> similarityEdges </td>
   <td style="text-align:right;"> 1,097,079 </td>
   <td style="text-align:right;"> 3,510,267 </td>
   <td style="text-align:left;"> 3.19964833890723 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 802 </td>
   <td style="text-align:right;"> 67,406 </td>
   <td style="text-align:left;"> 84.0473815461347 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connectAllSimilar </td>
   <td style="text-align:right;"> 361 </td>
   <td style="text-align:right;"> 3,608,971 </td>
   <td style="text-align:left;"> 9997.14958448754 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> cycleSearch </td>
   <td style="text-align:right;"> 160 </td>
   <td style="text-align:right;"> 4,680 </td>
   <td style="text-align:left;"> 29.25 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorks </td>
   <td style="text-align:right;"> 160 </td>
   <td style="text-align:right;"> 655 </td>
   <td style="text-align:left;"> 4.09375 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> connect </td>
   <td style="text-align:right;"> 34 </td>
   <td style="text-align:right;"> 240 </td>
   <td style="text-align:left;"> 7.05882352941176 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> searchAndConnect </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 106,787 </td>
   <td style="text-align:left;"> 10678.7 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 3,292,764 </td>
   <td style="text-align:right;font-weight: bold;"> 14,471,225 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  1 

<button class="btn btn-primary" data-toggle="collapse" data-target="#ODTYXIFWGP"> Show/Hide foundCycle query </button> 
 <div id="ODTYXIFWGP" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-07-11-31-vI2Zz9--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  0 

<button class="btn btn-primary" data-toggle="collapse" data-target="#ATUEMYOGBJ"> Show/Hide foundPath query </button> 
 <div id="ATUEMYOGBJ" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-07-11-31-vI2Zz9--CV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
###  SIM09-07-11-31-vI2Zz9--DV 

#### simulationParameters 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:left;"> chainLength </th>
   <th style="text-align:left;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:left;"> randomWorksNumberMultiplier </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;width: 20em; "> 800 </td>
   <td style="text-align:left;width: 15em; "> 0.99 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 1 </td>
   <td style="text-align:left;width: 15em; "> 10 </td>
   <td style="text-align:left;width: 15em; "> 2 </td>
  </tr>
</tbody>
</table>
#### allEdgesByLabel

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 1,610 </td>
   <td style="text-align:right;width: 5em; "> 208,208 </td>
   <td style="text-align:right;width: 5em; "> 1,610 </td>
   <td style="text-align:right;width: 5em; "> 1,610 </td>
   <td style="text-align:right;width: 5em; "> 4,801 </td>
   <td style="text-align:right;width: 5em; "> 217,839 </td>
  </tr>
</tbody>
</table>
#### allVerticesByType

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:right;width: 5em; "> 801 </td>
   <td style="text-align:right;width: 5em; "> 3,220 </td>
   <td style="text-align:right;width: 5em; "> 1,610 </td>
   <td style="text-align:right;width: 5em; "> 5,631 </td>
  </tr>
</tbody>
</table>
#### distribution of similarityEdgesByWeight

<img src="index_files/figure-html/get-experiment-info-190.png" width="672" />

#### Degree distribution of item->similarity->item edges

<img src="index_files/figure-html/get-experiment-info-191.png" width="672" />

#### Degree distribution of agent->knows->agent edges

<img src="index_files/figure-html/get-experiment-info-192.png" width="672" />

#### Runing time by method 

<table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> key </th>
   <th style="text-align:right;"> doc_count </th>
   <th style="text-align:right;"> sum_wallTime_ms.value </th>
   <th style="text-align:left;"> avg_wallTime_ms.value </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> knowsAgent </td>
   <td style="text-align:right;"> 4,801 </td>
   <td style="text-align:right;"> 9,271 </td>
   <td style="text-align:left;"> 1.93105602999375 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> addItemToWork </td>
   <td style="text-align:right;"> 3,220 </td>
   <td style="text-align:right;"> 32,484 </td>
   <td style="text-align:left;"> 10.088198757764 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> ownsWork </td>
   <td style="text-align:right;"> 1,610 </td>
   <td style="text-align:right;"> 15,980 </td>
   <td style="text-align:left;"> 9.92546583850932 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> init </td>
   <td style="text-align:right;"> 803 </td>
   <td style="text-align:right;"> 72,237 </td>
   <td style="text-align:left;"> 89.958904109589 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> searchAndConnect </td>
   <td style="text-align:right;"> 791 </td>
   <td style="text-align:right;"> 7,452,832 </td>
   <td style="text-align:left;"> 9422.0379266751 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> individualCycleSearch </td>
   <td style="text-align:right;"> 168 </td>
   <td style="text-align:right;"> 6 </td>
   <td style="text-align:left;"> 0.0357142857142857 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorksItems </td>
   <td style="text-align:right;"> 18 </td>
   <td style="text-align:right;"> 76 </td>
   <td style="text-align:left;"> 4.22222222222222 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> cycleSearch </td>
   <td style="text-align:right;"> 9 </td>
   <td style="text-align:right;"> 54,502 </td>
   <td style="text-align:left;"> 6055.77777777778 </td>
  </tr>
  <tr>
   <td style="text-align:left;"> getWorks </td>
   <td style="text-align:right;"> 9 </td>
   <td style="text-align:right;"> 27,339 </td>
   <td style="text-align:left;"> 3037.66666666667 </td>
  </tr>
  <tr>
   <td style="text-align:left;font-weight: bold;"> Total </td>
   <td style="text-align:right;font-weight: bold;"> 11,429 </td>
   <td style="text-align:right;font-weight: bold;"> 7,664,727 </td>
   <td style="text-align:left;font-weight: bold;">  </td>
  </tr>
</tbody>
</table>
####  Found structures 

#####  Cycles 

Number of cycles found:  3 

<button class="btn btn-primary" data-toggle="collapse" data-target="#RLWKZGZTMZ"> Show/Hide foundCycle query </button> 
 <div id="RLWKZGZTMZ" class="collapse" >  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-07-11-31-vI2Zz9--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundCycle"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
#####  Paths 

Number of paths found:  3 

<button class="btn btn-primary" data-toggle="collapse" data-target="#KJFVGGDMOF"> Show/Hide foundPath query </button> 
 <div id="KJFVGGDMOF" class="collapse">  
     GET /filebeat-*/_search 
              { 
                "query": {
                  "bool": {
                    "must": [
                      { "match_phrase": {
                        "simulationId": "SIM09-07-11-31-vI2Zz9--DV"
                        }
                      },
                      { "bool": {
                        "should": [
                          { "match": {
                            "keyword": "foundPath"
                          }}
                        ]
                      }}
                    ]
                  }
                }
              }
     

</div>
## Scatterplots of experiment 

<img src="index_files/figure-html/get-experiment-info-193.png" width="672" /><img src="index_files/figure-html/get-experiment-info-194.png" width="672" /><img src="index_files/figure-html/get-experiment-info-195.png" width="672" /><img src="index_files/figure-html/get-experiment-info-196.png" width="672" /><img src="index_files/figure-html/get-experiment-info-197.png" width="672" /><img src="index_files/figure-html/get-experiment-info-198.png" width="672" /><img src="index_files/figure-html/get-experiment-info-199.png" width="672" /><img src="index_files/figure-html/get-experiment-info-200.png" width="672" /><img src="index_files/figure-html/get-experiment-info-201.png" width="672" /><img src="index_files/figure-html/get-experiment-info-202.png" width="672" /><img src="index_files/figure-html/get-experiment-info-203.png" width="672" /><img src="index_files/figure-html/get-experiment-info-204.png" width="672" />  

# Cross analysis of all additional experiments

## Summary data 

<div style="border: 1px solid #ddd; padding: 5px; overflow-x: scroll; width:1300px; "><table class="table table-condensed" style="width: auto !important; ">
 <thead>
  <tr>
   <th style="text-align:left;"> experimentId </th>
   <th style="text-align:left;"> simulationId </th>
   <th style="text-align:right;"> agentNumber </th>
   <th style="text-align:left;"> similarityConnectThreshold </th>
   <th style="text-align:right;"> chainLength </th>
   <th style="text-align:right;"> similaritySearchThreshold </th>
   <th style="text-align:left;"> maxDistance </th>
   <th style="text-align:right;"> randomWorksNumberMultiplier </th>
   <th style="text-align:right;"> edges_offers </th>
   <th style="text-align:right;"> edges_similarity </th>
   <th style="text-align:right;"> edges_owns </th>
   <th style="text-align:right;"> edges_demands </th>
   <th style="text-align:right;"> edges_knows </th>
   <th style="text-align:right;"> edges_total </th>
   <th style="text-align:right;"> vertices_agent </th>
   <th style="text-align:right;"> vertices_item </th>
   <th style="text-align:right;"> vertices_work </th>
   <th style="text-align:right;"> vertices_total </th>
   <th style="text-align:right;"> sum_wallTime_ms_total </th>
   <th style="text-align:right;"> process_total </th>
   <th style="text-align:right;"> foundCycles </th>
   <th style="text-align:right;"> foundPaths </th>
   <th style="text-align:right;"> sum_wallTime_min_total </th>
   <th style="text-align:left;"> simulationType </th>
  </tr>
 </thead>
<tbody>
  <tr>
   <td style="text-align:left;"> EXP09-05-02-15-an9jYp </td>
   <td style="text-align:left;"> SIM09-05-02-15-GbI7At--CV </td>
   <td style="text-align:right;"> 400 </td>
   <td style="text-align:left;"> 0.9 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 410 </td>
   <td style="text-align:right;"> 131,000 </td>
   <td style="text-align:right;"> 410 </td>
   <td style="text-align:right;"> 410 </td>
   <td style="text-align:right;"> 2,401 </td>
   <td style="text-align:right;"> 134,631 </td>
   <td style="text-align:right;"> 401 </td>
   <td style="text-align:right;"> 820 </td>
   <td style="text-align:right;"> 410 </td>
   <td style="text-align:right;"> 1,631 </td>
   <td style="text-align:right;"> 6,637,292 </td>
   <td style="text-align:right;"> 1,008,812 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 0 </td>
   <td style="text-align:right;"> 110.62153 </td>
   <td style="text-align:left;"> Centralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-05-02-15-an9jYp </td>
   <td style="text-align:left;"> SIM09-05-02-15-GbI7At--DV </td>
   <td style="text-align:right;"> 400 </td>
   <td style="text-align:left;"> 0.9 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 410 </td>
   <td style="text-align:right;"> 130,561 </td>
   <td style="text-align:right;"> 410 </td>
   <td style="text-align:right;"> 410 </td>
   <td style="text-align:right;"> 2,401 </td>
   <td style="text-align:right;"> 134,192 </td>
   <td style="text-align:right;"> 401 </td>
   <td style="text-align:right;"> 820 </td>
   <td style="text-align:right;"> 410 </td>
   <td style="text-align:right;"> 1,631 </td>
   <td style="text-align:right;"> 1,383,851 </td>
   <td style="text-align:right;"> 4,564 </td>
   <td style="text-align:right;"> 18 </td>
   <td style="text-align:right;"> 0 </td>
   <td style="text-align:right;"> 23.06418 </td>
   <td style="text-align:left;"> Decentralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-05-02-15-an9jYp </td>
   <td style="text-align:left;"> SIM09-05-02-44-2F8BLS--CV </td>
   <td style="text-align:right;"> 400 </td>
   <td style="text-align:left;"> 0.93 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 410 </td>
   <td style="text-align:right;"> 94,248 </td>
   <td style="text-align:right;"> 410 </td>
   <td style="text-align:right;"> 410 </td>
   <td style="text-align:right;"> 2,401 </td>
   <td style="text-align:right;"> 97,879 </td>
   <td style="text-align:right;"> 401 </td>
   <td style="text-align:right;"> 820 </td>
   <td style="text-align:right;"> 410 </td>
   <td style="text-align:right;"> 1,631 </td>
   <td style="text-align:right;"> 5,252,740 </td>
   <td style="text-align:right;"> 1,008,742 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 0 </td>
   <td style="text-align:right;"> 87.54567 </td>
   <td style="text-align:left;"> Centralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-05-02-15-an9jYp </td>
   <td style="text-align:left;"> SIM09-05-02-44-2F8BLS--DV </td>
   <td style="text-align:right;"> 400 </td>
   <td style="text-align:left;"> 0.93 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 410 </td>
   <td style="text-align:right;"> 93,950 </td>
   <td style="text-align:right;"> 410 </td>
   <td style="text-align:right;"> 410 </td>
   <td style="text-align:right;"> 2,401 </td>
   <td style="text-align:right;"> 97,581 </td>
   <td style="text-align:right;"> 401 </td>
   <td style="text-align:right;"> 820 </td>
   <td style="text-align:right;"> 410 </td>
   <td style="text-align:right;"> 1,631 </td>
   <td style="text-align:right;"> 1,138,843 </td>
   <td style="text-align:right;"> 4,543 </td>
   <td style="text-align:right;"> 18 </td>
   <td style="text-align:right;"> 0 </td>
   <td style="text-align:right;"> 18.98072 </td>
   <td style="text-align:left;"> Decentralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-05-02-15-an9jYp </td>
   <td style="text-align:left;"> SIM09-05-03-07-a6ciiw--CV </td>
   <td style="text-align:right;"> 400 </td>
   <td style="text-align:left;"> 0.96 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 410 </td>
   <td style="text-align:right;"> 54,712 </td>
   <td style="text-align:right;"> 410 </td>
   <td style="text-align:right;"> 410 </td>
   <td style="text-align:right;"> 2,401 </td>
   <td style="text-align:right;"> 58,343 </td>
   <td style="text-align:right;"> 401 </td>
   <td style="text-align:right;"> 820 </td>
   <td style="text-align:right;"> 410 </td>
   <td style="text-align:right;"> 1,631 </td>
   <td style="text-align:right;"> 3,863,430 </td>
   <td style="text-align:right;"> 1,008,706 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 0 </td>
   <td style="text-align:right;"> 64.39050 </td>
   <td style="text-align:left;"> Centralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-05-02-15-an9jYp </td>
   <td style="text-align:left;"> SIM09-05-03-07-a6ciiw--DV </td>
   <td style="text-align:right;"> 400 </td>
   <td style="text-align:left;"> 0.96 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 410 </td>
   <td style="text-align:right;"> 54,484 </td>
   <td style="text-align:right;"> 410 </td>
   <td style="text-align:right;"> 410 </td>
   <td style="text-align:right;"> 2,401 </td>
   <td style="text-align:right;"> 58,115 </td>
   <td style="text-align:right;"> 401 </td>
   <td style="text-align:right;"> 820 </td>
   <td style="text-align:right;"> 410 </td>
   <td style="text-align:right;"> 1,631 </td>
   <td style="text-align:right;"> 857,413 </td>
   <td style="text-align:right;"> 4,495 </td>
   <td style="text-align:right;"> 3 </td>
   <td style="text-align:right;"> 0 </td>
   <td style="text-align:right;"> 14.29022 </td>
   <td style="text-align:left;"> Decentralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-05-02-15-an9jYp </td>
   <td style="text-align:left;"> SIM09-05-03-25-dk7PZu--CV </td>
   <td style="text-align:right;"> 400 </td>
   <td style="text-align:left;"> 0.99 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 410 </td>
   <td style="text-align:right;"> 13,554 </td>
   <td style="text-align:right;"> 410 </td>
   <td style="text-align:right;"> 410 </td>
   <td style="text-align:right;"> 2,401 </td>
   <td style="text-align:right;"> 17,185 </td>
   <td style="text-align:right;"> 401 </td>
   <td style="text-align:right;"> 820 </td>
   <td style="text-align:right;"> 410 </td>
   <td style="text-align:right;"> 1,631 </td>
   <td style="text-align:right;"> 2,265,068 </td>
   <td style="text-align:right;"> 1,008,617 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 0 </td>
   <td style="text-align:right;"> 37.75113 </td>
   <td style="text-align:left;"> Centralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-05-02-15-an9jYp </td>
   <td style="text-align:left;"> SIM09-05-03-25-dk7PZu--DV </td>
   <td style="text-align:right;"> 400 </td>
   <td style="text-align:left;"> 0.99 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 410 </td>
   <td style="text-align:right;"> 13,506 </td>
   <td style="text-align:right;"> 410 </td>
   <td style="text-align:right;"> 410 </td>
   <td style="text-align:right;"> 2,401 </td>
   <td style="text-align:right;"> 17,137 </td>
   <td style="text-align:right;"> 401 </td>
   <td style="text-align:right;"> 820 </td>
   <td style="text-align:right;"> 410 </td>
   <td style="text-align:right;"> 1,631 </td>
   <td style="text-align:right;"> 704,475 </td>
   <td style="text-align:right;"> 4,513 </td>
   <td style="text-align:right;"> 12 </td>
   <td style="text-align:right;"> 2 </td>
   <td style="text-align:right;"> 11.74125 </td>
   <td style="text-align:left;"> Decentralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-05-02-15-an9jYp </td>
   <td style="text-align:left;"> SIM09-05-03-35-JEKiMx--CV </td>
   <td style="text-align:right;"> 500 </td>
   <td style="text-align:left;"> 0.9 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 510 </td>
   <td style="text-align:right;"> 203,218 </td>
   <td style="text-align:right;"> 510 </td>
   <td style="text-align:right;"> 510 </td>
   <td style="text-align:right;"> 3,001 </td>
   <td style="text-align:right;"> 207,749 </td>
   <td style="text-align:right;"> 501 </td>
   <td style="text-align:right;"> 1,020 </td>
   <td style="text-align:right;"> 510 </td>
   <td style="text-align:right;"> 2,031 </td>
   <td style="text-align:right;"> 11,859,305 </td>
   <td style="text-align:right;"> 1,560,885 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 0 </td>
   <td style="text-align:right;"> 197.65508 </td>
   <td style="text-align:left;"> Centralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-05-02-15-an9jYp </td>
   <td style="text-align:left;"> SIM09-05-03-35-JEKiMx--DV </td>
   <td style="text-align:right;"> 500 </td>
   <td style="text-align:left;"> 0.9 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 510 </td>
   <td style="text-align:right;"> 202,595 </td>
   <td style="text-align:right;"> 510 </td>
   <td style="text-align:right;"> 510 </td>
   <td style="text-align:right;"> 3,001 </td>
   <td style="text-align:right;"> 207,126 </td>
   <td style="text-align:right;"> 501 </td>
   <td style="text-align:right;"> 1,020 </td>
   <td style="text-align:right;"> 510 </td>
   <td style="text-align:right;"> 2,031 </td>
   <td style="text-align:right;"> 2,195,155 </td>
   <td style="text-align:right;"> 5,748 </td>
   <td style="text-align:right;"> 33 </td>
   <td style="text-align:right;"> 6 </td>
   <td style="text-align:right;"> 36.58592 </td>
   <td style="text-align:left;"> Decentralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-05-02-15-an9jYp </td>
   <td style="text-align:left;"> SIM09-05-04-27-kUGfQG--CV </td>
   <td style="text-align:right;"> 500 </td>
   <td style="text-align:left;"> 0.93 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 510 </td>
   <td style="text-align:right;"> 145,916 </td>
   <td style="text-align:right;"> 510 </td>
   <td style="text-align:right;"> 510 </td>
   <td style="text-align:right;"> 3,001 </td>
   <td style="text-align:right;"> 150,447 </td>
   <td style="text-align:right;"> 501 </td>
   <td style="text-align:right;"> 1,020 </td>
   <td style="text-align:right;"> 510 </td>
   <td style="text-align:right;"> 2,031 </td>
   <td style="text-align:right;"> 9,524,031 </td>
   <td style="text-align:right;"> 1,560,793 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 0 </td>
   <td style="text-align:right;"> 158.73385 </td>
   <td style="text-align:left;"> Centralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-05-02-15-an9jYp </td>
   <td style="text-align:left;"> SIM09-05-04-27-kUGfQG--DV </td>
   <td style="text-align:right;"> 500 </td>
   <td style="text-align:left;"> 0.93 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 510 </td>
   <td style="text-align:right;"> 145,516 </td>
   <td style="text-align:right;"> 510 </td>
   <td style="text-align:right;"> 510 </td>
   <td style="text-align:right;"> 3,001 </td>
   <td style="text-align:right;"> 150,047 </td>
   <td style="text-align:right;"> 501 </td>
   <td style="text-align:right;"> 1,020 </td>
   <td style="text-align:right;"> 510 </td>
   <td style="text-align:right;"> 2,031 </td>
   <td style="text-align:right;"> 1,924,583 </td>
   <td style="text-align:right;"> 5,697 </td>
   <td style="text-align:right;"> 30 </td>
   <td style="text-align:right;"> 0 </td>
   <td style="text-align:right;"> 32.07638 </td>
   <td style="text-align:left;"> Decentralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-05-02-15-an9jYp </td>
   <td style="text-align:left;"> SIM09-05-05-09-yMqjcT--CV </td>
   <td style="text-align:right;"> 500 </td>
   <td style="text-align:left;"> 0.96 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 510 </td>
   <td style="text-align:right;"> 85,058 </td>
   <td style="text-align:right;"> 510 </td>
   <td style="text-align:right;"> 510 </td>
   <td style="text-align:right;"> 3,001 </td>
   <td style="text-align:right;"> 89,589 </td>
   <td style="text-align:right;"> 501 </td>
   <td style="text-align:right;"> 1,020 </td>
   <td style="text-align:right;"> 510 </td>
   <td style="text-align:right;"> 2,031 </td>
   <td style="text-align:right;"> 6,917,006 </td>
   <td style="text-align:right;"> 1,560,765 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 0 </td>
   <td style="text-align:right;"> 115.28343 </td>
   <td style="text-align:left;"> Centralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-05-02-15-an9jYp </td>
   <td style="text-align:left;"> SIM09-05-05-09-yMqjcT--DV </td>
   <td style="text-align:right;"> 500 </td>
   <td style="text-align:left;"> 0.96 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 510 </td>
   <td style="text-align:right;"> 84,806 </td>
   <td style="text-align:right;"> 510 </td>
   <td style="text-align:right;"> 510 </td>
   <td style="text-align:right;"> 3,001 </td>
   <td style="text-align:right;"> 89,337 </td>
   <td style="text-align:right;"> 501 </td>
   <td style="text-align:right;"> 1,020 </td>
   <td style="text-align:right;"> 510 </td>
   <td style="text-align:right;"> 2,031 </td>
   <td style="text-align:right;"> 1,334,379 </td>
   <td style="text-align:right;"> 5,596 </td>
   <td style="text-align:right;"> 3 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 22.23965 </td>
   <td style="text-align:left;"> Decentralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-05-02-15-an9jYp </td>
   <td style="text-align:left;"> SIM09-05-05-39-kF9FXt--CV </td>
   <td style="text-align:right;"> 500 </td>
   <td style="text-align:left;"> 0.99 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 510 </td>
   <td style="text-align:right;"> 21,000 </td>
   <td style="text-align:right;"> 510 </td>
   <td style="text-align:right;"> 510 </td>
   <td style="text-align:right;"> 3,001 </td>
   <td style="text-align:right;"> 25,531 </td>
   <td style="text-align:right;"> 501 </td>
   <td style="text-align:right;"> 1,020 </td>
   <td style="text-align:right;"> 510 </td>
   <td style="text-align:right;"> 2,031 </td>
   <td style="text-align:right;"> 3,846,247 </td>
   <td style="text-align:right;"> 1,560,623 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 0 </td>
   <td style="text-align:right;"> 64.10412 </td>
   <td style="text-align:left;"> Centralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-05-02-15-an9jYp </td>
   <td style="text-align:left;"> SIM09-05-05-39-kF9FXt--DV </td>
   <td style="text-align:right;"> 500 </td>
   <td style="text-align:left;"> 0.99 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 510 </td>
   <td style="text-align:right;"> 20,940 </td>
   <td style="text-align:right;"> 510 </td>
   <td style="text-align:right;"> 510 </td>
   <td style="text-align:right;"> 3,001 </td>
   <td style="text-align:right;"> 25,471 </td>
   <td style="text-align:right;"> 501 </td>
   <td style="text-align:right;"> 1,020 </td>
   <td style="text-align:right;"> 510 </td>
   <td style="text-align:right;"> 2,031 </td>
   <td style="text-align:right;"> 1,114,833 </td>
   <td style="text-align:right;"> 5,640 </td>
   <td style="text-align:right;"> 13 </td>
   <td style="text-align:right;"> 0 </td>
   <td style="text-align:right;"> 18.58055 </td>
   <td style="text-align:left;"> Decentralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-05-02-15-an9jYp </td>
   <td style="text-align:left;"> SIM09-05-05-57-fvqRNr--CV </td>
   <td style="text-align:right;"> 600 </td>
   <td style="text-align:left;"> 0.9 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 610 </td>
   <td style="text-align:right;"> 286,882 </td>
   <td style="text-align:right;"> 610 </td>
   <td style="text-align:right;"> 610 </td>
   <td style="text-align:right;"> 3,601 </td>
   <td style="text-align:right;"> 292,313 </td>
   <td style="text-align:right;"> 601 </td>
   <td style="text-align:right;"> 1,220 </td>
   <td style="text-align:right;"> 610 </td>
   <td style="text-align:right;"> 2,431 </td>
   <td style="text-align:right;"> 14,316,191 </td>
   <td style="text-align:right;"> 1,541,654 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 0 </td>
   <td style="text-align:right;"> 238.60318 </td>
   <td style="text-align:left;"> Centralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-05-02-15-an9jYp </td>
   <td style="text-align:left;"> SIM09-05-05-57-fvqRNr--DV </td>
   <td style="text-align:right;"> 600 </td>
   <td style="text-align:left;"> 0.9 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 610 </td>
   <td style="text-align:right;"> 286,442 </td>
   <td style="text-align:right;"> 610 </td>
   <td style="text-align:right;"> 610 </td>
   <td style="text-align:right;"> 3,601 </td>
   <td style="text-align:right;"> 291,873 </td>
   <td style="text-align:right;"> 601 </td>
   <td style="text-align:right;"> 1,220 </td>
   <td style="text-align:right;"> 610 </td>
   <td style="text-align:right;"> 2,431 </td>
   <td style="text-align:right;"> 4,099,450 </td>
   <td style="text-align:right;"> 6,916 </td>
   <td style="text-align:right;"> 40 </td>
   <td style="text-align:right;"> 0 </td>
   <td style="text-align:right;"> 68.32417 </td>
   <td style="text-align:left;"> Decentralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-05-02-15-an9jYp </td>
   <td style="text-align:left;"> SIM09-05-07-00-zrM33e--CV </td>
   <td style="text-align:right;"> 600 </td>
   <td style="text-align:left;"> 0.93 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 610 </td>
   <td style="text-align:right;"> 206,192 </td>
   <td style="text-align:right;"> 610 </td>
   <td style="text-align:right;"> 610 </td>
   <td style="text-align:right;"> 3,601 </td>
   <td style="text-align:right;"> 211,623 </td>
   <td style="text-align:right;"> 601 </td>
   <td style="text-align:right;"> 1,220 </td>
   <td style="text-align:right;"> 610 </td>
   <td style="text-align:right;"> 2,431 </td>
   <td style="text-align:right;"> 14,297,145 </td>
   <td style="text-align:right;"> 2,065,649 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 0 </td>
   <td style="text-align:right;"> 238.28575 </td>
   <td style="text-align:left;"> Centralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-05-02-15-an9jYp </td>
   <td style="text-align:left;"> SIM09-05-07-00-zrM33e--DV </td>
   <td style="text-align:right;"> 600 </td>
   <td style="text-align:left;"> 0.93 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 610 </td>
   <td style="text-align:right;"> 205,746 </td>
   <td style="text-align:right;"> 610 </td>
   <td style="text-align:right;"> 610 </td>
   <td style="text-align:right;"> 3,601 </td>
   <td style="text-align:right;"> 211,177 </td>
   <td style="text-align:right;"> 601 </td>
   <td style="text-align:right;"> 1,220 </td>
   <td style="text-align:right;"> 610 </td>
   <td style="text-align:right;"> 2,431 </td>
   <td style="text-align:right;"> 2,954,302 </td>
   <td style="text-align:right;"> 6,881 </td>
   <td style="text-align:right;"> 35 </td>
   <td style="text-align:right;"> 0 </td>
   <td style="text-align:right;"> 49.23837 </td>
   <td style="text-align:left;"> Decentralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-05-02-15-an9jYp </td>
   <td style="text-align:left;"> SIM09-05-08-02-Lrzqji--CV </td>
   <td style="text-align:right;"> 600 </td>
   <td style="text-align:left;"> 0.96 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 610 </td>
   <td style="text-align:right;"> 119,194 </td>
   <td style="text-align:right;"> 610 </td>
   <td style="text-align:right;"> 610 </td>
   <td style="text-align:right;"> 3,601 </td>
   <td style="text-align:right;"> 124,625 </td>
   <td style="text-align:right;"> 601 </td>
   <td style="text-align:right;"> 1,220 </td>
   <td style="text-align:right;"> 610 </td>
   <td style="text-align:right;"> 2,431 </td>
   <td style="text-align:right;"> 11,025,247 </td>
   <td style="text-align:right;"> 2,232,706 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 0 </td>
   <td style="text-align:right;"> 183.75412 </td>
   <td style="text-align:left;"> Centralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-05-02-15-an9jYp </td>
   <td style="text-align:left;"> SIM09-05-08-02-Lrzqji--DV </td>
   <td style="text-align:right;"> 600 </td>
   <td style="text-align:left;"> 0.96 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 610 </td>
   <td style="text-align:right;"> 118,968 </td>
   <td style="text-align:right;"> 610 </td>
   <td style="text-align:right;"> 610 </td>
   <td style="text-align:right;"> 3,601 </td>
   <td style="text-align:right;"> 124,399 </td>
   <td style="text-align:right;"> 601 </td>
   <td style="text-align:right;"> 1,220 </td>
   <td style="text-align:right;"> 610 </td>
   <td style="text-align:right;"> 2,431 </td>
   <td style="text-align:right;"> 2,229,390 </td>
   <td style="text-align:right;"> 6,818 </td>
   <td style="text-align:right;"> 25 </td>
   <td style="text-align:right;"> 0 </td>
   <td style="text-align:right;"> 37.15650 </td>
   <td style="text-align:left;"> Decentralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-05-02-15-an9jYp </td>
   <td style="text-align:left;"> SIM09-05-08-51-5PgQ8m--CV </td>
   <td style="text-align:right;"> 600 </td>
   <td style="text-align:left;"> 0.99 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 610 </td>
   <td style="text-align:right;"> 30,004 </td>
   <td style="text-align:right;"> 610 </td>
   <td style="text-align:right;"> 610 </td>
   <td style="text-align:right;"> 3,601 </td>
   <td style="text-align:right;"> 35,435 </td>
   <td style="text-align:right;"> 601 </td>
   <td style="text-align:right;"> 1,220 </td>
   <td style="text-align:right;"> 610 </td>
   <td style="text-align:right;"> 2,431 </td>
   <td style="text-align:right;"> 5,889,239 </td>
   <td style="text-align:right;"> 2,232,627 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 0 </td>
   <td style="text-align:right;"> 98.15398 </td>
   <td style="text-align:left;"> Centralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-05-02-15-an9jYp </td>
   <td style="text-align:left;"> SIM09-05-08-51-5PgQ8m--DV </td>
   <td style="text-align:right;"> 600 </td>
   <td style="text-align:left;"> 0.99 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 610 </td>
   <td style="text-align:right;"> 29,934 </td>
   <td style="text-align:right;"> 610 </td>
   <td style="text-align:right;"> 610 </td>
   <td style="text-align:right;"> 3,601 </td>
   <td style="text-align:right;"> 35,365 </td>
   <td style="text-align:right;"> 601 </td>
   <td style="text-align:right;"> 1,220 </td>
   <td style="text-align:right;"> 610 </td>
   <td style="text-align:right;"> 2,431 </td>
   <td style="text-align:right;"> 1,468,875 </td>
   <td style="text-align:right;"> 6,773 </td>
   <td style="text-align:right;"> 18 </td>
   <td style="text-align:right;"> 7 </td>
   <td style="text-align:right;"> 24.48125 </td>
   <td style="text-align:left;"> Decentralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-05-11-49-gcWcHs </td>
   <td style="text-align:left;"> SIM09-05-11-49-Zfitmw--CV </td>
   <td style="text-align:right;"> 700 </td>
   <td style="text-align:left;"> 0.9 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 710 </td>
   <td style="text-align:right;"> 390,090 </td>
   <td style="text-align:right;"> 710 </td>
   <td style="text-align:right;"> 710 </td>
   <td style="text-align:right;"> 4,201 </td>
   <td style="text-align:right;"> 396,421 </td>
   <td style="text-align:right;"> 701 </td>
   <td style="text-align:right;"> 1,420 </td>
   <td style="text-align:right;"> 710 </td>
   <td style="text-align:right;"> 2,831 </td>
   <td style="text-align:right;"> 14,625,509 </td>
   <td style="text-align:right;"> 1,355,977 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 0 </td>
   <td style="text-align:right;"> 243.75848 </td>
   <td style="text-align:left;"> Centralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-05-11-49-gcWcHs </td>
   <td style="text-align:left;"> SIM09-05-11-49-Zfitmw--DV </td>
   <td style="text-align:right;"> 700 </td>
   <td style="text-align:left;"> 0.9 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 710 </td>
   <td style="text-align:right;"> 389,714 </td>
   <td style="text-align:right;"> 710 </td>
   <td style="text-align:right;"> 710 </td>
   <td style="text-align:right;"> 4,201 </td>
   <td style="text-align:right;"> 396,045 </td>
   <td style="text-align:right;"> 701 </td>
   <td style="text-align:right;"> 1,420 </td>
   <td style="text-align:right;"> 710 </td>
   <td style="text-align:right;"> 2,831 </td>
   <td style="text-align:right;"> 5,658,721 </td>
   <td style="text-align:right;"> 7,840 </td>
   <td style="text-align:right;"> 3 </td>
   <td style="text-align:right;"> 0 </td>
   <td style="text-align:right;"> 94.31202 </td>
   <td style="text-align:left;"> Decentralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-05-11-49-gcWcHs </td>
   <td style="text-align:left;"> SIM09-06-01-57-weh222--CV </td>
   <td style="text-align:right;"> 700 </td>
   <td style="text-align:left;"> 0.96 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 710 </td>
   <td style="text-align:right;"> 161,698 </td>
   <td style="text-align:right;"> 710 </td>
   <td style="text-align:right;"> 710 </td>
   <td style="text-align:right;"> 4,201 </td>
   <td style="text-align:right;"> 168,029 </td>
   <td style="text-align:right;"> 701 </td>
   <td style="text-align:right;"> 1,420 </td>
   <td style="text-align:right;"> 710 </td>
   <td style="text-align:right;"> 2,831 </td>
   <td style="text-align:right;"> 14,291,888 </td>
   <td style="text-align:right;"> 2,585,991 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 0 </td>
   <td style="text-align:right;"> 238.19813 </td>
   <td style="text-align:left;"> Centralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-05-11-49-gcWcHs </td>
   <td style="text-align:left;"> SIM09-06-01-57-weh222--DV </td>
   <td style="text-align:right;"> 700 </td>
   <td style="text-align:left;"> 0.96 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 710 </td>
   <td style="text-align:right;"> 161,400 </td>
   <td style="text-align:right;"> 710 </td>
   <td style="text-align:right;"> 710 </td>
   <td style="text-align:right;"> 4,201 </td>
   <td style="text-align:right;"> 167,731 </td>
   <td style="text-align:right;"> 701 </td>
   <td style="text-align:right;"> 1,420 </td>
   <td style="text-align:right;"> 710 </td>
   <td style="text-align:right;"> 2,831 </td>
   <td style="text-align:right;"> 3,020,464 </td>
   <td style="text-align:right;"> 7,984 </td>
   <td style="text-align:right;"> 30 </td>
   <td style="text-align:right;"> 0 </td>
   <td style="text-align:right;"> 50.34107 </td>
   <td style="text-align:left;"> Decentralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-05-11-49-gcWcHs </td>
   <td style="text-align:left;"> SIM09-06-02-59-TNfsQt--CV </td>
   <td style="text-align:right;"> 700 </td>
   <td style="text-align:left;"> 0.99 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 710 </td>
   <td style="text-align:right;"> 40,964 </td>
   <td style="text-align:right;"> 710 </td>
   <td style="text-align:right;"> 710 </td>
   <td style="text-align:right;"> 4,201 </td>
   <td style="text-align:right;"> 47,295 </td>
   <td style="text-align:right;"> 701 </td>
   <td style="text-align:right;"> 1,420 </td>
   <td style="text-align:right;"> 710 </td>
   <td style="text-align:right;"> 2,831 </td>
   <td style="text-align:right;"> 8,535,076 </td>
   <td style="text-align:right;"> 3,024,644 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 0 </td>
   <td style="text-align:right;"> 142.25127 </td>
   <td style="text-align:left;"> Centralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-05-11-49-gcWcHs </td>
   <td style="text-align:left;"> SIM09-06-02-59-TNfsQt--DV </td>
   <td style="text-align:right;"> 700 </td>
   <td style="text-align:left;"> 0.99 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 710 </td>
   <td style="text-align:right;"> 40,862 </td>
   <td style="text-align:right;"> 710 </td>
   <td style="text-align:right;"> 710 </td>
   <td style="text-align:right;"> 4,201 </td>
   <td style="text-align:right;"> 47,193 </td>
   <td style="text-align:right;"> 701 </td>
   <td style="text-align:right;"> 1,420 </td>
   <td style="text-align:right;"> 710 </td>
   <td style="text-align:right;"> 2,831 </td>
   <td style="text-align:right;"> 2,317,391 </td>
   <td style="text-align:right;"> 7,942 </td>
   <td style="text-align:right;"> 18 </td>
   <td style="text-align:right;"> 0 </td>
   <td style="text-align:right;"> 38.62318 </td>
   <td style="text-align:left;"> Decentralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-05-11-49-gcWcHs </td>
   <td style="text-align:left;"> SIM09-06-12-53-gtcCvK--CV </td>
   <td style="text-align:right;"> 700 </td>
   <td style="text-align:left;"> 0.93 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 710 </td>
   <td style="text-align:right;"> 278,904 </td>
   <td style="text-align:right;"> 710 </td>
   <td style="text-align:right;"> 710 </td>
   <td style="text-align:right;"> 4,201 </td>
   <td style="text-align:right;"> 285,235 </td>
   <td style="text-align:right;"> 701 </td>
   <td style="text-align:right;"> 1,420 </td>
   <td style="text-align:right;"> 710 </td>
   <td style="text-align:right;"> 2,831 </td>
   <td style="text-align:right;"> 14,317,300 </td>
   <td style="text-align:right;"> 1,780,383 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 0 </td>
   <td style="text-align:right;"> 238.62167 </td>
   <td style="text-align:left;"> Centralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-05-11-49-gcWcHs </td>
   <td style="text-align:left;"> SIM09-06-12-53-gtcCvK--DV </td>
   <td style="text-align:right;"> 700 </td>
   <td style="text-align:left;"> 0.93 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 710 </td>
   <td style="text-align:right;"> 278,618 </td>
   <td style="text-align:right;"> 710 </td>
   <td style="text-align:right;"> 710 </td>
   <td style="text-align:right;"> 4,201 </td>
   <td style="text-align:right;"> 284,949 </td>
   <td style="text-align:right;"> 701 </td>
   <td style="text-align:right;"> 1,420 </td>
   <td style="text-align:right;"> 710 </td>
   <td style="text-align:right;"> 2,831 </td>
   <td style="text-align:right;"> 4,383,763 </td>
   <td style="text-align:right;"> 8,030 </td>
   <td style="text-align:right;"> 41 </td>
   <td style="text-align:right;"> 0 </td>
   <td style="text-align:right;"> 73.06272 </td>
   <td style="text-align:left;"> Decentralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-05-11-49-gcWcHs </td>
   <td style="text-align:left;"> SIM09-06-03-37-UDs3YI--CV </td>
   <td style="text-align:right;"> 800 </td>
   <td style="text-align:left;"> 0.9 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 810 </td>
   <td style="text-align:right;"> 507,696 </td>
   <td style="text-align:right;"> 810 </td>
   <td style="text-align:right;"> 810 </td>
   <td style="text-align:right;"> 4,801 </td>
   <td style="text-align:right;"> 514,927 </td>
   <td style="text-align:right;"> 801 </td>
   <td style="text-align:right;"> 1,620 </td>
   <td style="text-align:right;"> 810 </td>
   <td style="text-align:right;"> 3,231 </td>
   <td style="text-align:right;"> 14,557,056 </td>
   <td style="text-align:right;"> 1,187,787 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 0 </td>
   <td style="text-align:right;"> 242.61760 </td>
   <td style="text-align:left;"> Centralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-05-11-49-gcWcHs </td>
   <td style="text-align:left;"> SIM09-06-03-37-UDs3YI--DV </td>
   <td style="text-align:right;"> 800 </td>
   <td style="text-align:left;"> 0.9 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 810 </td>
   <td style="text-align:right;"> 507,393 </td>
   <td style="text-align:right;"> 810 </td>
   <td style="text-align:right;"> 810 </td>
   <td style="text-align:right;"> 4,801 </td>
   <td style="text-align:right;"> 514,624 </td>
   <td style="text-align:right;"> 801 </td>
   <td style="text-align:right;"> 1,620 </td>
   <td style="text-align:right;"> 810 </td>
   <td style="text-align:right;"> 3,231 </td>
   <td style="text-align:right;"> 8,410,217 </td>
   <td style="text-align:right;"> 9,026 </td>
   <td style="text-align:right;"> 3 </td>
   <td style="text-align:right;"> 0 </td>
   <td style="text-align:right;"> 140.17028 </td>
   <td style="text-align:left;"> Decentralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-05-11-49-gcWcHs </td>
   <td style="text-align:left;"> SIM09-06-04-43-xhtRRy--CV </td>
   <td style="text-align:right;"> 800 </td>
   <td style="text-align:left;"> 0.93 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 810 </td>
   <td style="text-align:right;"> 359,148 </td>
   <td style="text-align:right;"> 810 </td>
   <td style="text-align:right;"> 810 </td>
   <td style="text-align:right;"> 4,801 </td>
   <td style="text-align:right;"> 366,379 </td>
   <td style="text-align:right;"> 801 </td>
   <td style="text-align:right;"> 1,620 </td>
   <td style="text-align:right;"> 810 </td>
   <td style="text-align:right;"> 3,231 </td>
   <td style="text-align:right;"> 14,451,820 </td>
   <td style="text-align:right;"> 1,616,282 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 0 </td>
   <td style="text-align:right;"> 240.86367 </td>
   <td style="text-align:left;"> Centralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-05-11-49-gcWcHs </td>
   <td style="text-align:left;"> SIM09-06-04-43-xhtRRy--DV </td>
   <td style="text-align:right;"> 800 </td>
   <td style="text-align:left;"> 0.93 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 810 </td>
   <td style="text-align:right;"> 358,884 </td>
   <td style="text-align:right;"> 810 </td>
   <td style="text-align:right;"> 810 </td>
   <td style="text-align:right;"> 4,801 </td>
   <td style="text-align:right;"> 366,115 </td>
   <td style="text-align:right;"> 801 </td>
   <td style="text-align:right;"> 1,620 </td>
   <td style="text-align:right;"> 810 </td>
   <td style="text-align:right;"> 3,231 </td>
   <td style="text-align:right;"> 5,769,842 </td>
   <td style="text-align:right;"> 8,988 </td>
   <td style="text-align:right;"> 3 </td>
   <td style="text-align:right;"> 4 </td>
   <td style="text-align:right;"> 96.16403 </td>
   <td style="text-align:left;"> Decentralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-05-11-49-gcWcHs </td>
   <td style="text-align:left;"> SIM09-06-05-48-vZUWy3--CV </td>
   <td style="text-align:right;"> 800 </td>
   <td style="text-align:left;"> 0.96 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 810 </td>
   <td style="text-align:right;"> 209,080 </td>
   <td style="text-align:right;"> 810 </td>
   <td style="text-align:right;"> 810 </td>
   <td style="text-align:right;"> 4,801 </td>
   <td style="text-align:right;"> 216,311 </td>
   <td style="text-align:right;"> 801 </td>
   <td style="text-align:right;"> 1,620 </td>
   <td style="text-align:right;"> 810 </td>
   <td style="text-align:right;"> 3,231 </td>
   <td style="text-align:right;"> 14,315,421 </td>
   <td style="text-align:right;"> 2,318,007 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 0 </td>
   <td style="text-align:right;"> 238.59035 </td>
   <td style="text-align:left;"> Centralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-05-11-49-gcWcHs </td>
   <td style="text-align:left;"> SIM09-06-05-48-vZUWy3--DV </td>
   <td style="text-align:right;"> 800 </td>
   <td style="text-align:left;"> 0.96 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 810 </td>
   <td style="text-align:right;"> 208,826 </td>
   <td style="text-align:right;"> 810 </td>
   <td style="text-align:right;"> 810 </td>
   <td style="text-align:right;"> 4,801 </td>
   <td style="text-align:right;"> 216,057 </td>
   <td style="text-align:right;"> 801 </td>
   <td style="text-align:right;"> 1,620 </td>
   <td style="text-align:right;"> 810 </td>
   <td style="text-align:right;"> 3,231 </td>
   <td style="text-align:right;"> 3,868,392 </td>
   <td style="text-align:right;"> 9,106 </td>
   <td style="text-align:right;"> 21 </td>
   <td style="text-align:right;"> 5 </td>
   <td style="text-align:right;"> 64.47320 </td>
   <td style="text-align:left;"> Decentralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-05-11-49-gcWcHs </td>
   <td style="text-align:left;"> SIM09-06-06-51-hiGdwx--CV </td>
   <td style="text-align:right;"> 800 </td>
   <td style="text-align:left;"> 0.99 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 810 </td>
   <td style="text-align:right;"> 52,374 </td>
   <td style="text-align:right;"> 810 </td>
   <td style="text-align:right;"> 810 </td>
   <td style="text-align:right;"> 4,801 </td>
   <td style="text-align:right;"> 59,605 </td>
   <td style="text-align:right;"> 801 </td>
   <td style="text-align:right;"> 1,620 </td>
   <td style="text-align:right;"> 810 </td>
   <td style="text-align:right;"> 3,231 </td>
   <td style="text-align:right;"> 11,793,098 </td>
   <td style="text-align:right;"> 3,936,771 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 0 </td>
   <td style="text-align:right;"> 196.55163 </td>
   <td style="text-align:left;"> Centralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-05-11-49-gcWcHs </td>
   <td style="text-align:left;"> SIM09-06-06-51-hiGdwx--DV </td>
   <td style="text-align:right;"> 800 </td>
   <td style="text-align:left;"> 0.99 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 810 </td>
   <td style="text-align:right;"> 52,250 </td>
   <td style="text-align:right;"> 810 </td>
   <td style="text-align:right;"> 810 </td>
   <td style="text-align:right;"> 4,801 </td>
   <td style="text-align:right;"> 59,481 </td>
   <td style="text-align:right;"> 801 </td>
   <td style="text-align:right;"> 1,620 </td>
   <td style="text-align:right;"> 810 </td>
   <td style="text-align:right;"> 3,231 </td>
   <td style="text-align:right;"> 2,742,258 </td>
   <td style="text-align:right;"> 8,930 </td>
   <td style="text-align:right;"> 3 </td>
   <td style="text-align:right;"> 6 </td>
   <td style="text-align:right;"> 45.70430 </td>
   <td style="text-align:left;"> Decentralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-07-02-54-hATxwM </td>
   <td style="text-align:left;"> SIM09-07-02-54-AmUqkP--CV </td>
   <td style="text-align:right;"> 700 </td>
   <td style="text-align:left;"> 0.9 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 2 </td>
   <td style="text-align:right;"> 1,410 </td>
   <td style="text-align:right;"> 1,542,412 </td>
   <td style="text-align:right;"> 1,410 </td>
   <td style="text-align:right;"> 1,410 </td>
   <td style="text-align:right;"> 4,201 </td>
   <td style="text-align:right;"> 1,550,843 </td>
   <td style="text-align:right;"> 701 </td>
   <td style="text-align:right;"> 2,820 </td>
   <td style="text-align:right;"> 1,410 </td>
   <td style="text-align:right;"> 4,931 </td>
   <td style="text-align:right;"> 14,366,052 </td>
   <td style="text-align:right;"> 808,922 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 0 </td>
   <td style="text-align:right;"> 239.43420 </td>
   <td style="text-align:left;"> Centralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-07-02-54-hATxwM </td>
   <td style="text-align:left;"> SIM09-07-02-54-AmUqkP--DV </td>
   <td style="text-align:right;"> 700 </td>
   <td style="text-align:left;"> 0.9 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 2 </td>
   <td style="text-align:right;"> 1,410 </td>
   <td style="text-align:right;"> 1,542,107 </td>
   <td style="text-align:right;"> 1,410 </td>
   <td style="text-align:right;"> 1,410 </td>
   <td style="text-align:right;"> 4,201 </td>
   <td style="text-align:right;"> 1,550,538 </td>
   <td style="text-align:right;"> 701 </td>
   <td style="text-align:right;"> 2,820 </td>
   <td style="text-align:right;"> 1,410 </td>
   <td style="text-align:right;"> 4,931 </td>
   <td style="text-align:right;"> 33,780,862 </td>
   <td style="text-align:right;"> 11,178 </td>
   <td style="text-align:right;"> 129 </td>
   <td style="text-align:right;"> 4 </td>
   <td style="text-align:right;"> 563.01437 </td>
   <td style="text-align:left;"> Decentralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-07-02-54-hATxwM </td>
   <td style="text-align:left;"> SIM09-07-04-13-Tqhuy8--CV </td>
   <td style="text-align:right;"> 700 </td>
   <td style="text-align:left;"> 0.93 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 2 </td>
   <td style="text-align:right;"> 1,410 </td>
   <td style="text-align:right;"> 1,084,042 </td>
   <td style="text-align:right;"> 1,410 </td>
   <td style="text-align:right;"> 1,410 </td>
   <td style="text-align:right;"> 4,201 </td>
   <td style="text-align:right;"> 1,092,473 </td>
   <td style="text-align:right;"> 701 </td>
   <td style="text-align:right;"> 2,820 </td>
   <td style="text-align:right;"> 1,410 </td>
   <td style="text-align:right;"> 4,931 </td>
   <td style="text-align:right;"> 14,420,311 </td>
   <td style="text-align:right;"> 1,076,719 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 0 </td>
   <td style="text-align:right;"> 240.33852 </td>
   <td style="text-align:left;"> Centralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-07-02-54-hATxwM </td>
   <td style="text-align:left;"> SIM09-07-04-13-Tqhuy8--DV </td>
   <td style="text-align:right;"> 700 </td>
   <td style="text-align:left;"> 0.93 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 2 </td>
   <td style="text-align:right;"> 1,410 </td>
   <td style="text-align:right;"> 1,083,862 </td>
   <td style="text-align:right;"> 1,410 </td>
   <td style="text-align:right;"> 1,410 </td>
   <td style="text-align:right;"> 4,201 </td>
   <td style="text-align:right;"> 1,092,293 </td>
   <td style="text-align:right;"> 701 </td>
   <td style="text-align:right;"> 2,820 </td>
   <td style="text-align:right;"> 1,410 </td>
   <td style="text-align:right;"> 4,931 </td>
   <td style="text-align:right;"> 21,116,845 </td>
   <td style="text-align:right;"> 10,697 </td>
   <td style="text-align:right;"> 109 </td>
   <td style="text-align:right;"> 0 </td>
   <td style="text-align:right;"> 351.94742 </td>
   <td style="text-align:left;"> Decentralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-07-02-54-hATxwM </td>
   <td style="text-align:left;"> SIM09-07-05-25-o9GkLq--CV </td>
   <td style="text-align:right;"> 700 </td>
   <td style="text-align:left;"> 0.96 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 2 </td>
   <td style="text-align:right;"> 1,410 </td>
   <td style="text-align:right;"> 634,532 </td>
   <td style="text-align:right;"> 1,410 </td>
   <td style="text-align:right;"> 1,410 </td>
   <td style="text-align:right;"> 4,201 </td>
   <td style="text-align:right;"> 642,963 </td>
   <td style="text-align:right;"> 701 </td>
   <td style="text-align:right;"> 2,820 </td>
   <td style="text-align:right;"> 1,410 </td>
   <td style="text-align:right;"> 4,931 </td>
   <td style="text-align:right;"> 14,362,543 </td>
   <td style="text-align:right;"> 1,427,703 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 0 </td>
   <td style="text-align:right;"> 239.37572 </td>
   <td style="text-align:left;"> Centralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-07-02-54-hATxwM </td>
   <td style="text-align:left;"> SIM09-07-05-25-o9GkLq--DV </td>
   <td style="text-align:right;"> 700 </td>
   <td style="text-align:left;"> 0.96 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 2 </td>
   <td style="text-align:right;"> 1,410 </td>
   <td style="text-align:right;"> 634,404 </td>
   <td style="text-align:right;"> 1,410 </td>
   <td style="text-align:right;"> 1,410 </td>
   <td style="text-align:right;"> 4,201 </td>
   <td style="text-align:right;"> 642,835 </td>
   <td style="text-align:right;"> 701 </td>
   <td style="text-align:right;"> 2,820 </td>
   <td style="text-align:right;"> 1,410 </td>
   <td style="text-align:right;"> 4,931 </td>
   <td style="text-align:right;"> 10,277,950 </td>
   <td style="text-align:right;"> 10,271 </td>
   <td style="text-align:right;"> 26 </td>
   <td style="text-align:right;"> 5 </td>
   <td style="text-align:right;"> 171.29917 </td>
   <td style="text-align:left;"> Decentralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-07-02-54-hATxwM </td>
   <td style="text-align:left;"> SIM09-07-06-32-2MR3Ho--CV </td>
   <td style="text-align:right;"> 700 </td>
   <td style="text-align:left;"> 0.99 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 2 </td>
   <td style="text-align:right;"> 1,410 </td>
   <td style="text-align:right;"> 159,898 </td>
   <td style="text-align:right;"> 1,410 </td>
   <td style="text-align:right;"> 1,410 </td>
   <td style="text-align:right;"> 4,201 </td>
   <td style="text-align:right;"> 168,329 </td>
   <td style="text-align:right;"> 701 </td>
   <td style="text-align:right;"> 2,820 </td>
   <td style="text-align:right;"> 1,410 </td>
   <td style="text-align:right;"> 4,931 </td>
   <td style="text-align:right;"> 14,320,231 </td>
   <td style="text-align:right;"> 3,617,360 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 0 </td>
   <td style="text-align:right;"> 238.67052 </td>
   <td style="text-align:left;"> Centralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-07-02-54-hATxwM </td>
   <td style="text-align:left;"> SIM09-07-06-32-2MR3Ho--DV </td>
   <td style="text-align:right;"> 700 </td>
   <td style="text-align:left;"> 0.99 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 2 </td>
   <td style="text-align:right;"> 1,410 </td>
   <td style="text-align:right;"> 159,802 </td>
   <td style="text-align:right;"> 1,410 </td>
   <td style="text-align:right;"> 1,410 </td>
   <td style="text-align:right;"> 4,201 </td>
   <td style="text-align:right;"> 168,233 </td>
   <td style="text-align:right;"> 701 </td>
   <td style="text-align:right;"> 2,820 </td>
   <td style="text-align:right;"> 1,410 </td>
   <td style="text-align:right;"> 4,931 </td>
   <td style="text-align:right;"> 5,706,242 </td>
   <td style="text-align:right;"> 10,083 </td>
   <td style="text-align:right;"> 3 </td>
   <td style="text-align:right;"> 2 </td>
   <td style="text-align:right;"> 95.10403 </td>
   <td style="text-align:left;"> Decentralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-07-02-54-hATxwM </td>
   <td style="text-align:left;"> SIM09-07-07-36-mFck8t--CV </td>
   <td style="text-align:right;"> 800 </td>
   <td style="text-align:left;"> 0.9 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 2 </td>
   <td style="text-align:right;"> 1,610 </td>
   <td style="text-align:right;"> 2,010,342 </td>
   <td style="text-align:right;"> 1,610 </td>
   <td style="text-align:right;"> 1,610 </td>
   <td style="text-align:right;"> 4,801 </td>
   <td style="text-align:right;"> 2,019,973 </td>
   <td style="text-align:right;"> 801 </td>
   <td style="text-align:right;"> 3,220 </td>
   <td style="text-align:right;"> 1,610 </td>
   <td style="text-align:right;"> 5,631 </td>
   <td style="text-align:right;"> 14,460,684 </td>
   <td style="text-align:right;"> 672,197 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 0 </td>
   <td style="text-align:right;"> 241.01140 </td>
   <td style="text-align:left;"> Centralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-07-02-54-hATxwM </td>
   <td style="text-align:left;"> SIM09-07-07-36-mFck8t--DV </td>
   <td style="text-align:right;"> 800 </td>
   <td style="text-align:left;"> 0.9 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 2 </td>
   <td style="text-align:right;"> 1,610 </td>
   <td style="text-align:right;"> 2,010,169 </td>
   <td style="text-align:right;"> 1,610 </td>
   <td style="text-align:right;"> 1,610 </td>
   <td style="text-align:right;"> 4,801 </td>
   <td style="text-align:right;"> 2,019,800 </td>
   <td style="text-align:right;"> 801 </td>
   <td style="text-align:right;"> 3,220 </td>
   <td style="text-align:right;"> 1,610 </td>
   <td style="text-align:right;"> 5,631 </td>
   <td style="text-align:right;"> 53,471,418 </td>
   <td style="text-align:right;"> 12,943 </td>
   <td style="text-align:right;"> 126 </td>
   <td style="text-align:right;"> 0 </td>
   <td style="text-align:right;"> 891.19030 </td>
   <td style="text-align:left;"> Decentralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-07-02-54-hATxwM </td>
   <td style="text-align:left;"> SIM09-07-09-04-3czu4g--CV </td>
   <td style="text-align:right;"> 800 </td>
   <td style="text-align:left;"> 0.93 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 2 </td>
   <td style="text-align:right;"> 1,610 </td>
   <td style="text-align:right;"> 1,424,774 </td>
   <td style="text-align:right;"> 1,610 </td>
   <td style="text-align:right;"> 1,610 </td>
   <td style="text-align:right;"> 4,801 </td>
   <td style="text-align:right;"> 1,434,405 </td>
   <td style="text-align:right;"> 801 </td>
   <td style="text-align:right;"> 3,220 </td>
   <td style="text-align:right;"> 1,610 </td>
   <td style="text-align:right;"> 5,631 </td>
   <td style="text-align:right;"> 15,067,420 </td>
   <td style="text-align:right;"> 924,972 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 0 </td>
   <td style="text-align:right;"> 251.12367 </td>
   <td style="text-align:left;"> Centralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-07-02-54-hATxwM </td>
   <td style="text-align:left;"> SIM09-07-09-04-3czu4g--DV </td>
   <td style="text-align:right;"> 800 </td>
   <td style="text-align:left;"> 0.93 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 2 </td>
   <td style="text-align:right;"> 1,610 </td>
   <td style="text-align:right;"> 1,424,656 </td>
   <td style="text-align:right;"> 1,610 </td>
   <td style="text-align:right;"> 1,610 </td>
   <td style="text-align:right;"> 4,801 </td>
   <td style="text-align:right;"> 1,434,287 </td>
   <td style="text-align:right;"> 801 </td>
   <td style="text-align:right;"> 3,220 </td>
   <td style="text-align:right;"> 1,610 </td>
   <td style="text-align:right;"> 5,631 </td>
   <td style="text-align:right;"> 30,106,724 </td>
   <td style="text-align:right;"> 11,884 </td>
   <td style="text-align:right;"> 3 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 501.77873 </td>
   <td style="text-align:left;"> Decentralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-07-02-54-hATxwM </td>
   <td style="text-align:left;"> SIM09-07-10-22-fh4Yzs--CV </td>
   <td style="text-align:right;"> 800 </td>
   <td style="text-align:left;"> 0.96 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 2 </td>
   <td style="text-align:right;"> 1,610 </td>
   <td style="text-align:right;"> 821,174 </td>
   <td style="text-align:right;"> 1,610 </td>
   <td style="text-align:right;"> 1,610 </td>
   <td style="text-align:right;"> 4,801 </td>
   <td style="text-align:right;"> 830,805 </td>
   <td style="text-align:right;"> 801 </td>
   <td style="text-align:right;"> 3,220 </td>
   <td style="text-align:right;"> 1,610 </td>
   <td style="text-align:right;"> 5,631 </td>
   <td style="text-align:right;"> 14,344,053 </td>
   <td style="text-align:right;"> 1,370,546 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 0 </td>
   <td style="text-align:right;"> 239.06755 </td>
   <td style="text-align:left;"> Centralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-07-02-54-hATxwM </td>
   <td style="text-align:left;"> SIM09-07-10-22-fh4Yzs--DV </td>
   <td style="text-align:right;"> 800 </td>
   <td style="text-align:left;"> 0.96 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 2 </td>
   <td style="text-align:right;"> 1,610 </td>
   <td style="text-align:right;"> 821,082 </td>
   <td style="text-align:right;"> 1,610 </td>
   <td style="text-align:right;"> 1,610 </td>
   <td style="text-align:right;"> 4,801 </td>
   <td style="text-align:right;"> 830,713 </td>
   <td style="text-align:right;"> 801 </td>
   <td style="text-align:right;"> 3,220 </td>
   <td style="text-align:right;"> 1,610 </td>
   <td style="text-align:right;"> 5,631 </td>
   <td style="text-align:right;"> 15,399,666 </td>
   <td style="text-align:right;"> 11,974 </td>
   <td style="text-align:right;"> 99 </td>
   <td style="text-align:right;"> 4 </td>
   <td style="text-align:right;"> 256.66110 </td>
   <td style="text-align:left;"> Decentralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-07-02-54-hATxwM </td>
   <td style="text-align:left;"> SIM09-07-11-31-vI2Zz9--CV </td>
   <td style="text-align:right;"> 800 </td>
   <td style="text-align:left;"> 0.99 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 2 </td>
   <td style="text-align:right;"> 1,610 </td>
   <td style="text-align:right;"> 208,276 </td>
   <td style="text-align:right;"> 1,610 </td>
   <td style="text-align:right;"> 1,610 </td>
   <td style="text-align:right;"> 4,801 </td>
   <td style="text-align:right;"> 217,907 </td>
   <td style="text-align:right;"> 801 </td>
   <td style="text-align:right;"> 3,220 </td>
   <td style="text-align:right;"> 1,610 </td>
   <td style="text-align:right;"> 5,631 </td>
   <td style="text-align:right;"> 14,471,225 </td>
   <td style="text-align:right;"> 3,292,764 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:right;"> 0 </td>
   <td style="text-align:right;"> 241.18708 </td>
   <td style="text-align:left;"> Centralized </td>
  </tr>
  <tr>
   <td style="text-align:left;"> EXP09-07-02-54-hATxwM </td>
   <td style="text-align:left;"> SIM09-07-11-31-vI2Zz9--DV </td>
   <td style="text-align:right;"> 800 </td>
   <td style="text-align:left;"> 0.99 </td>
   <td style="text-align:right;"> 10 </td>
   <td style="text-align:right;"> 1 </td>
   <td style="text-align:left;"> 10 </td>
   <td style="text-align:right;"> 2 </td>
   <td style="text-align:right;"> 1,610 </td>
   <td style="text-align:right;"> 208,208 </td>
   <td style="text-align:right;"> 1,610 </td>
   <td style="text-align:right;"> 1,610 </td>
   <td style="text-align:right;"> 4,801 </td>
   <td style="text-align:right;"> 217,839 </td>
   <td style="text-align:right;"> 801 </td>
   <td style="text-align:right;"> 3,220 </td>
   <td style="text-align:right;"> 1,610 </td>
   <td style="text-align:right;"> 5,631 </td>
   <td style="text-align:right;"> 7,664,727 </td>
   <td style="text-align:right;"> 11,429 </td>
   <td style="text-align:right;"> 3 </td>
   <td style="text-align:right;"> 3 </td>
   <td style="text-align:right;"> 127.74545 </td>
   <td style="text-align:left;"> Decentralized </td>
  </tr>
</tbody>
</table></div>
## Scatterplots of all experiments 

<img src="index_files/figure-html/get-experiment-info-205.png" width="672" /><img src="index_files/figure-html/get-experiment-info-206.png" width="672" /><img src="index_files/figure-html/get-experiment-info-207.png" width="672" /><img src="index_files/figure-html/get-experiment-info-208.png" width="672" /><img src="index_files/figure-html/get-experiment-info-209.png" width="672" /><img src="index_files/figure-html/get-experiment-info-210.png" width="672" /><img src="index_files/figure-html/get-experiment-info-211.png" width="672" /><img src="index_files/figure-html/get-experiment-info-212.png" width="672" /><img src="index_files/figure-html/get-experiment-info-213.png" width="672" /><img src="index_files/figure-html/get-experiment-info-214.png" width="672" /><img src="index_files/figure-html/get-experiment-info-215.png" width="672" /><img src="index_files/figure-html/get-experiment-info-216.png" width="672" /><img src="index_files/figure-html/get-experiment-info-217.png" width="672" /><img src="index_files/figure-html/get-experiment-info-218.png" width="672" /><img src="index_files/figure-html/get-experiment-info-219.png" width="672" /><img src="index_files/figure-html/get-experiment-info-220.png" width="672" /><img src="index_files/figure-html/get-experiment-info-221.png" width="672" /><img src="index_files/figure-html/get-experiment-info-222.png" width="672" /><img src="index_files/figure-html/get-experiment-info-223.png" width="672" /><img src="index_files/figure-html/get-experiment-info-224.png" width="672" /><img src="index_files/figure-html/get-experiment-info-225.png" width="672" /><img src="index_files/figure-html/get-experiment-info-226.png" width="672" /><img src="index_files/figure-html/get-experiment-info-227.png" width="672" /><img src="index_files/figure-html/get-experiment-info-228.png" width="672" /><img src="index_files/figure-html/get-experiment-info-229.png" width="672" /><img src="index_files/figure-html/get-experiment-info-230.png" width="672" /><img src="index_files/figure-html/get-experiment-info-231.png" width="672" /><img src="index_files/figure-html/get-experiment-info-232.png" width="672" /><img src="index_files/figure-html/get-experiment-info-233.png" width="672" /><img src="index_files/figure-html/get-experiment-info-234.png" width="672" /><img src="index_files/figure-html/get-experiment-info-235.png" width="672" /><img src="index_files/figure-html/get-experiment-info-236.png" width="672" /><img src="index_files/figure-html/get-experiment-info-237.png" width="672" /><img src="index_files/figure-html/get-experiment-info-238.png" width="672" /><img src="index_files/figure-html/get-experiment-info-239.png" width="672" /><img src="index_files/figure-html/get-experiment-info-240.png" width="672" /><img src="index_files/figure-html/get-experiment-info-241.png" width="672" /><img src="index_files/figure-html/get-experiment-info-242.png" width="672" /><img src="index_files/figure-html/get-experiment-info-243.png" width="672" /><img src="index_files/figure-html/get-experiment-info-244.png" width="672" /><img src="index_files/figure-html/get-experiment-info-245.png" width="672" /><img src="index_files/figure-html/get-experiment-info-246.png" width="672" /><img src="index_files/figure-html/get-experiment-info-247.png" width="672" /><img src="index_files/figure-html/get-experiment-info-248.png" width="672" /><img src="index_files/figure-html/get-experiment-info-249.png" width="672" /><img src="index_files/figure-html/get-experiment-info-250.png" width="672" /><img src="index_files/figure-html/get-experiment-info-251.png" width="672" /><img src="index_files/figure-html/get-experiment-info-252.png" width="672" />
