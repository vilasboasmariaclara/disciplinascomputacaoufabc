## **Rodovias**
Uma rodovia é considerada crítica se existem duas cidades A e B tais que para se deslocar da cidade A para a cidade B, é obrigatório passar por essa rodovia.

O administrador das rodovias do país estava fazendo uma análise, e percebeu que em algumas regiões existiam rodovias críticas.

Isso é problemático e uma fragilidade na malha viária do país pois, no caso de alguma interdição dessas rodovias as cidades A e B perderiam o contato.

Esse administrador, percebendo o problema, contratou você, um nobre computeiro formado na prestigiosa Universidade Federal do ABC, para ajudá-lo na identificação de tais rodovias.

Para realizar tal tarefa ele vai te dar o mapa rodoviário do país, e você deve identificar quantas rodovias críticas existem.

## **Critérios importantes**
Independente dos resultados dos testes o não cumprimento dos critérios abaixo implicará em nota zero para esta atividade. Qualquer dúvida, entre em contato.

Você deve resolver esse problema usando grafos, que devem ser representados por listas de adjacências.

> **Observações**
> - Você deve incluir, no início do seu programa, uma breve cabeçalho contendo no mínimo o seu nome e RA.
> - Indente corretamente o seu código e inclua comentários no decorrer do seu programa.
> - Linguagens aceitas: C

### **Entrada**
A primeira linha da entrada consiste de dois inteiros n m, onde 1 <= n <= 1000 e 0 <= m <= n(n - 1)/2, representando o número de cidades e o número de rodovias do país, respectivamente.

Cada cidade é representada por um código numérico entre 0 e n - 1.

Cada uma das próximas m linhas consiste de um par de inteiros u v, separados por espaço, onde 0 <= u, v <= n - 1, que representa a existência de uma rodovia ligando as cidades de códigos u e v.

### **Saída**
A resposta do seu programa deve consistir de um único inteiro z, representando o número de rodovias críticas presentes no país.

**Exemplos**
Teste 01

Entrada:
```
3 3
0 1
0 2
1 2 
```

Saída:
```
0
```

Teste 02

Entrada:
```
6 5
0 1
1 2
1 3
3 4
3 5
```

Saída:
```
5
```

Teste 03

Entrada:
```
8 10
0 1
0 2
1 2
1 3
2 3
3 4
4 5
4 6
5 6
5 7
```

Saída:
```
2
```
