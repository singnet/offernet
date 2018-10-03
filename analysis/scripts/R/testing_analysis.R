conn <-connect(es_port = 9200)
template <- '{    "query": {
            "bool": {
              "must": [
                {"exists" : { "field" : "method" }},
                {"exists" : { "field" : "simulationId" }},
                { "bool": {
                  "should": [
                      {"match_phrase": {
                        "method": "allEdgesByLabel"
                      }},
                      {"match_phrase": {
                        "method": "allVerticesByLabel"
                      }},
                      {"match_phrase": {
                        "method": "similarityEdgesByWeight"
                      }}
                  ]
                }}
              ]
            }
        }}'
query <- sprintf(template)
source=c('simulationId','results', 'message', 'method','edgeLabel','vertexType','direction')
result <- Search(index="filebeat-*", source=source, body=query, asdf=TRUE, size=10000)$hits$hits

analysis.df <- result[,-c(1:4)]
vars=sapply(strsplit(colnames(analysis.df), split='.', fixed=TRUE), function(x) (x[2]))
colnames(analysis.df) <- vars

cat(paste('### ',simulationId,'\n\n'))
cat(paste('#### simulationParameters \n\n'))

simulation.df <- simulations.df[which(simulations.df$simulationId == simulationId),][,-1]
rownames(simulation.df) <-c()

for_sum.df <-data.frame()
for_sum.df <- simulation.df
for_sum.df$experimentId <-  experimentId
for_sum.df$simulationId <- simulationId


# output simulationParameters
print(kable(simulation.df) %>%
        kable_styling(bootstrap_options='condensed',full_width = FALSE, position = 'left') %>%
        column_spec(1:1,width="20em") %>%
        column_spec(2:ncol(simulation.df),width="15em")
      #add_header_above(c("variables"))
)
simulationId <- 'SIM09-27-08-30-rp7cU3--CV'
simulationData <- analysis.df[which(analysis.df$simulationId == simulationId),]

# output allEdgesByLabel
edges.df <- data.frame(fromJSON(simulationData[which(simulationData$method == 'allEdgesByLabel'),]$results))
names <- colnames(edges.df)
names <- paste('edges',names,sep="_")
colnames(edges.df) <- names

edges.df$edges_total <- rowSums(edges.df)
for_sum.df <- cbind(for_sum.df,edges.df)
cat('#### allEdgesByLabel\n\n')
print(kable(edges.df, format.args = list(decimal.mark = '.', big.mark = ",")) %>%
        kable_styling(bootstrap_options='condensed',full_width = FALSE, position = 'left') %>%
        column_spec(1:ncol(edges.df),width="5em") 
      #add_header_above(c("labels"=5))
)

# output allVerticesByType
vertices.df <- data.frame(fromJSON(simulationData[which(simulationData$method == 'allVerticesByLabel'),]$results))
names <- colnames(vertices.df)
names <- paste('vertices',names,sep="_")
colnames(vertices.df) <- names

vertices.df$vertices_total <- rowSums(vertices.df)
for_sum.df <- cbind(for_sum.df,vertices.df)
cat('#### allVerticesByType\n\n')
print(kable(vertices.df, format.args = list(decimal.mark = '.', big.mark = ",")) %>%
        kable_styling(bootstrap_options='condensed',full_width = FALSE, position = 'left') %>%
        column_spec(1:ncol(vertices.df),width="5em") 
      #add_header_above(c("labels"=5))
)

# output similarityEdgesByWeight
similarity <- data.frame(fromJSON(simulationData[which(simulationData$method == 'similarityEdgesByWeight'),]$results))
wghts <- as.numeric(substring(unlist(names(similarity)),first = 2))
frequencies <- as.numeric(similarity)
similarity.df <- data.frame(weight=wghts,edges=frequencies)
se <- ggplot(data=similarity.df, aes(x=weight, y=frequencies)) +
  geom_bar(stat="identity", fill="steelblue")+
  geom_text(aes(label=weight), vjust=1.6, color="white", size=3.5)+
  xlab("Weight")+
  ylab("Number of edges")+
  theme_minimal()+
  geom_line()

cat('#### distribution of similarityEdgesByWeight\n\n')
print(se)
cat('\n\n')