# Method overlap

## Centralized search:

1. connectIfSimilar -> included in 5;
2. existsSimilarity -> included in 1
3. similarityEdges -> included in 2
4. connect -> included 1
5. connectAllSimilar -> included in 7;
6. init -- take initialization out
7. connectAllSimilarCentralized +
8. cycleSearch +

Sum only these methods to get totals:
7 - ConnectAllSimilarCentralized,
8 - cycleSearch

run - 1
1	connectIfSimilar	232,609	10,914,094	46.9203427210469
2	existsSimilarity	232,609	10,729,118	46.1251198362918
3	similarityEdges	232,609	10,331,737	44.4167551556475
4	connect	67,423	175,762	2.60685522744464
5	init	503	22,791	45.3101391650099
6	connectAllSimilar	58	10,917,553	188233.672413793
10	connectAllSimilarCentralized	1	10,917,559	10917559

run - 2
connectIfSimilar	87,990	280,495	3.1878054324355
existsSimilarity	87,990	278,272	3.16254119786339
similarityEdges	87,990	271,983	3.09106716672349
connectAllSimilar	420	281,428	670.066666666667
connect	199	418	2.10050251256281
init	103	9,982	96.9126213592233

run-3
connectIfSimilar	268,787	3,602,162	13.4015484379825
existsSimilarity	268,787	3,593,238	13.368347427517
similarityEdges	268,787	3,498,343	13.0152983589236
cycleSearch	836	34,170	40.8732057416268
getWorks	835	2,938	3.5185628742515
init	703	29,347	41.7453769559033
connectAllSimilar	97	3,605,499	37170.0927835052
connect



## De-centralized search:

1. addItemToWork -- out
2. ownsWork -- out
3. init -- take this out
4. knowsAgent -- take out because this is initialization of the network
5. searchAndConnect +
6. cycleSearch +
7. getWorks -> included in 6
8. individualCycleSearch +
9. getWorksItems -- we take this out because it is needed to setup the network and not the search; 

Sum only these methods to get totals:
5 - searchAndConnect,
6 - cycleSearch
8 - individualCycleSearch


