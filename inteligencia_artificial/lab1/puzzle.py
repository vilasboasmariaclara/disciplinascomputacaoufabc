"""
UFABC
Inteligência Artificial - Laboratório 1

Maria Clara Vilas Boas, R.A. 11100115

Input:
    Uma matriz de inteiros, em python representada por uma lista de listas: 
        [[int, int, int],
         [int, int, int],
         [int, int, int]]
    Exemplo:
        [[1, 8, 2],
         [_, 4, 3],
         [7, 6, 5]]

Output:
    Impressão de cada estado do quebra-cabeça até a chegado do estado final


---
Referências:
https://blog.goodaudience.com/solving-8-puzzle-using-a-algorithm-7b509c331288
https://rosettacode.org/wiki/A*_search_algorithm


"""
_LINE = "______________________________________________________________\n"

_INFO_START = """
O formato digitado deve ser o de uma matriz 3x3 - em python uma lista de listas.
Exemplo: 
    [[1, 8, 2],
     ['_', 4, 3],
     [7, 6, 5]]

Caso queira seguir com o exemplo acima como input, digite INI
"""

_INFO_GOAL = """
O formato digitado deve ser o de uma matriz 3x3 - em python uma lista de listas.
Exemplo: 
    [[1, 2, 3],
     [4, 5, 6],
     [7, 8, '_']]

Caso queira seguir com o exemplo acima como input, digite FIN
"""

_ARROW = """
    |
   \\\'/

"""


class Node:


    def __init__(self, data, level, fval):
        """ Initialize the node with the data, level of the node and the calculated fvalue """
        self.data = data
        self.level = level
        self.fval = fval


    def get_neighbours(self):
            """ Generate neighbours node from the given node by moving the blank space
                either in the four directions {up, down, left, right} """
            x, y = self.find(self.data, '_')
            
            """ val_list contains position values for moving the blank space in either of
                the 4 directions [up,down,left,right] respectively. """
            val_list = [[x, y-1], [x, y+1], [x-1, y], [x+1, y]]
            response = []
            
            for i in val_list:
                neighbour = self.shuffle(self.data, x, y, i[0], i[1])
                if neighbour:
                    neighbour_node = Node(neighbour, self.level+1, 0)
                    response.append(neighbour_node)
            
            return response

            
    def shuffle(self, puz, x1, y1, x2, y2):
        """ Move the blank space in the given direction and if the position value are out
            of limits the return None """

        if x2 >= 0 and x2 < len(self.data) and y2 >= 0 and y2 < len(self.data):
            temp_puz = []
            temp_puz = self.copy(puz)

            temp = temp_puz[x2][y2]
            
            temp_puz[x2][y2] = temp_puz[x1][y1]
            temp_puz[x1][y1] = temp

            return temp_puz

        else:            
            return None


    def copy(self, root):
        """ Copy function to create a similar matrix of the given node"""
        temp = []
        for i in root:
            t = []
            for j in i:
                t.append(j)
            temp.append(t)
        return temp    
                

    def find(self, puz, x):
        """ Specifically used to find the position of the blank space """
        for i in range(0, len(self.data)):
            for j in range(0, len(self.data)):
                if puz[i][j] == x:
                    return i,j


class Puzzle:


    def __init__(self, start_state, goal_state):
        """ Initialize the puzzle size by the specified size, open and closed lists to empty """
        self.n           = 3
        
        self.start_state = start_state
        self.goal_state  = goal_state

        self.current     = None

        self.open        = []
        self.closed      = []


    def h(self, start):
        """ Calculates the different between the given puzzles """
        distance = 0

        for x1 in range(0, self.n):
            for y1 in range(0, self.n):

                
                for x2 in range(0, self.n):
                    for y2 in range(0, self.n):

                        if start[x1][y1] == self.goal_state[x2][y2]:

                            distance += abs(x1 - x2) + abs(y1 - y2)

        return distance


    def f(self, start):
        """ Heuristic Function to calculate hueristic value f(x) = h(x) + g(x) """
        return self.h(start.data) + start.level


    def print_state(self):
        for i in self.current.data:
            for j in i:
                print(j, end=" ")
            print("")


    def solve(self):

        node = Node(start, 0, 0)
        node.fval = self.f(node)

        self.open.append(node)

        while len(self.open) > 0:
            self.current = self.open[0]
            print(f'{_ARROW}')

            self.print_state()

            """ If the difference between current and goal node is 0 we have reached the goal node"""
            if(self.h(self.current.data) == 0):
                break

            for i in self.current.get_neighbours():
                i.fval = self.f(i)
                self.open.append(i)

            self.closed.append(self.current)

            del self.open[0]

            """ sort the open list based on f value """
            self.open.sort(key = lambda x: x.fval, reverse=False)




if __name__ == '__main__':


    print(f'{_LINE}1) Digite o estado inicial do quebra-cabeça:\n{_INFO_START}')
    _input = input()
    start = [[1, 8, 2], ['_', 4, 3], [7, 6, 5]] if _input == 'INI' else _input


    print(f'{_LINE}2) Digite o estado final do quebra-cabeça:\n{_INFO_GOAL}')
    _input = input()
    goal = [[1, 2, 3], [4, 5, 6], [7, 8, '_']] if _input == 'FIN' else _input

    print(f'{_LINE}3) Resolvendo . . .')

    puzzle = Puzzle(start_state=start, goal_state=goal)
    puzzle.solve()