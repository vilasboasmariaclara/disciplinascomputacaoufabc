#include <assert.h>
#include <stdio.h>
#include <stdlib.h>

/*
TEORIA DOS GRAFOS: TRABALHO 1
PROFº MAICON SAMBINELLI | UFABC Q3.2022 

A IMPLEMENTAÇÃO AQUI CONTIDA FORAM BASEADAS NOS 
IMPLEMENTAÇÕES DISPONIBILIZADOS NO ARQUIVO "05 - Representacoes.pdf"
E NO PSEUDOCÓDIGO PRESENTE NO ARQUIVO "06 - Busca Em Grafos", 
AMBOS DISPONÍVEIS NO MOODLE DA DISCIPLINA.

O OBJETIVO É IMPLEMENTAR O ALGORITMO DE BUSCA DFS E ADAPTÁ-LO
PARA ENCONTRAR O NÚMERO DE ARESTAS DE CORTE DE UM GRAFO G, E,
DESSA FORMA RESOLVER A APLICAÇÃO PRESENTE DO ENUNCIADO NO TRABALHO 1.
*/

// Definição dos tipos abstratos de dados

#ifndef __GRAPH_H_
#define __GRAPH_H_

typedef int Vertex;
typedef struct {Vertex u; Vertex v;} Edge;
typedef struct graph* Graph;

Edge edge(Vertex, Vertex);
Graph graph(int);

void graph_destroy(Graph);
void graph_insert_edge(Graph, Edge);
void graph_print(Graph);
void graph_print_edges(Graph);
int graph_neighbors(Graph, int, int*);

#endif // __GRAPH_H_

// Estruturas de dados

typedef struct node *link;


struct node {
	int w;
	link next;
};


Edge edge(int u, int v) {
	Edge e = {u, v};
	return e;
}


struct graph {
	int V;
	int E;
	int n_cut_edges;

	link *adj;
  
	int *vis;
	int *pred;
	int *low;
	int *ord;
};


// Métodos e funções

Graph graph(int V) {

	Graph G = malloc(sizeof(*G));
	
	G->V 		   = V;
	G->E 		   = 0;
 	G->n_cut_edges = 0;

 	G->adj 		   = malloc(V * sizeof(link));
  	G->vis		   = malloc(V * sizeof(*(G->vis)));
  	G->pred 	   = malloc(V * sizeof(*(G->pred)));
  	G->low 		   = malloc(V * sizeof(*(G->low)));
  	G->ord		   = malloc(V * sizeof(*(G->ord)));
	
	for (int u = 0; u < V; u++)
		G->adj[u] = NULL;
	
	return G;
}


void graph_destroy(Graph G) {

  for (int u = 0; u < G->V; u++)
      free(G->adj[u]);

  free(G->vis);
  free(G->pred);
  free(G->low);
  free(G->ord);
  free(G);
}


link list_insert(link head, int w) {
	link p = malloc(sizeof(*p));
	p->w = w;
	p->next = head;
	return p;
}


void graph_insert_edge(Graph G, Edge e) {
	G->adj[e.u] = list_insert(G->adj[e.u], e.v);
	G->adj[e.v] = list_insert(G->adj[e.v], e.u);
	G->E++;
}


void graph_print_edges(Graph G) {
  for (int u = 0; u < G->V; u++)
    for (link p = G->adj[u]; p != NULL; p = p->next) 
      if (u < p->w)
        printf("%d %d\n", u, p->w);
}

void graph_print(Graph G) {
	printf("V: %d, E: %d\n", G->V, G->E);
	graph_print_edges(G);
}


int graph_neighbors(Graph G, int u, int* neigh) {
    int i = 0;
    for (link p = G->adj[u]; p != NULL; p = p->next) {
        neigh[i] = p->w;
        i += 1;
    }
    return i;
}

int min(int a, int b) {
	return (a > b) ? b : a;
}


void dfs_search_corte(Graph G, int u, int *count) {

	// G->vis_order += 1;
	
 	G->vis[u] = 1;

	*count += 1;

	G->ord[u] = *count;
	G->low[u] = *count;

	for (link v = G->adj[u]; v != NULL; v = v->next) {
    	if (G->vis[v->w] == 0) {

    		G->pred[v->w] = u;

    		dfs_search_corte(G, v->w, count);

    		if (G->low[v->w] == G->ord[v->w]){
    			G->n_cut_edges += 1;
    		}

    		G->low[u] = min(G->low[u], G->low[v->w]);
    	}
    	else {
    		if (v->w != G->pred[u])
    			G->low[u] = min(G->low[u], G->ord[v->w]);
    	}
  	}
}



int dfs_corte(Graph G) {

	for (int u = 0; u < G->V; u++) {
		G->vis[u]  = 0;
		G->pred[u] = -1;
		G->ord[u]  = -1;
		G->low[u]  = -1;
	}

	int count = 0;

	G->pred[0] = 0;

	dfs_search_corte(G, 0, &count);

	return G->n_cut_edges;

}

// Execução do fluxo

int main(int argc, char const *argv[]) {

	int V, E, response;

	scanf("%d %d", &V, &E);

	Graph G = graph(V);

	for (int i = 0; i < E; i++) {
		int u, v;
		scanf("%d %d", &u, &v);
		graph_insert_edge(G, edge(u, v));
	}

	response = dfs_corte(G);

	printf("%d", response);

	graph_destroy(G);

	return 0;
}

