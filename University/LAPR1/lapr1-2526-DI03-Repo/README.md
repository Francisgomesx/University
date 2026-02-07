<div align="center">

# BitMasters - Abelian Sandpile Model

### Projeto Integrador de LAPR1 2025/2026

[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://www.oracle.com/java/)
[![Apache Commons Math](https://img.shields.io/badge/Apache%20Commons%20Math-3.6.1-blue.svg)](https://commons.apache.org/proper/commons-math/)

*Simulação de balanceamento de carga em sistemas distribuídos usando o Modelo de Sandpile Abeliano*

</div>

---

## 📋 Sobre o Projeto

Aplicação Java que simula a distribuição de tarefas em sistemas de servidores através do **Abelian Sandpile Model**. Implementa algoritmos para análise de estabilidade, estados recorrentes e propriedades algébricas de matrizes de configuração.

**Conceitos principais:**
- **Threshold:** 4 tarefas por servidor
- **Toppling:** Redistribuição automática para vizinhos ortogonais
- **Estados Recorrentes:** Configurações alcançáveis através de operações sucessivas

---

## ⚡ Funcionalidades

| # | Funcionalidade | Descrição |
|---|----------------|-----------|
| 1 | Carregar Matriz | Carrega e exibe matriz de ficheiro CSV |
| 2 | Estabilizar | Verifica estabilidade e estabiliza matriz |
| 3 | Heatmap | Soma duas matrizes (A ⊕ B) com snapshots visuais |
| 4 | Testar Recorrência | Aplica Algoritmo de Burning de Dhar |
| 5 | Elemento Neutro | Verifica se matriz é elemento neutro |
| 6 | Contar Recorrentes | Enumera estados recorrentes |
| 7 | Contar Recorrentes (Laplaciano) | Usa determinante da Laplaciana reduzida |
| 8 | Inversa Estabilizada | Calcula o inverso de um estado recorrente |
| 9 | Autovalores/Autovetores | Decomposição espetral da Laplaciana |
| 10 | Fórmula Fechada | Cálculo analítico de autovalores/autovetores |

---

## 🛠️ Tecnologias

- **Java 17+**
- **Apache Commons Math 3.6.1** - Operações matriciais avançadas
- **JUnit** - Testes unitários

---

## 📦 Instalação

### Pré-requisitos
```bash
java -version  # Verificar Java 17+
```

### Passos

1. **Clone o repositório**
```bash
git clone https://github.com/Departamento-de-Engenharia-Informatica/lapr1-2526-DI03-Repo.git
cd lapr1-2526-DI03-Repo
```

2. **Baixe Apache Commons Math 3.6.1** (versão correta e compatível)

   **📥 [Download Direto: commons-math3-3.6.1.jar](https://repo.maven.apache.org/maven2/org/apache/commons/commons-math3/3.6.1/commons-math3-3.6.1.jar)** (2.2 MB)

   Manualmente:
   ```bash
   # Windows (PowerShell)
   New-Item -ItemType Directory -Force -Path lib
   Invoke-WebRequest -Uri "https://repo.maven.apache.org/maven2/org/apache/commons/commons-math3/3.6.1/commons-math3-3.6.1.jar" -OutFile "lib/commons-math3-3.6.1.jar"

   # Linux/Mac
   mkdir -p lib
   wget https://repo.maven.apache.org/maven2/org/apache/commons/commons-math3/3.6.1/commons-math3-3.6.1.jar -P lib/
   # ou use curl:
   curl -o lib/commons-math3-3.6.1.jar https://repo.maven.apache.org/maven2/org/apache/commons/commons-math3/3.6.1/commons-math3-3.6.1.jar
   ```

   ⚠️ **IMPORTANTE:** Use apenas a versão **3.6.1** - outras versões podem ser incompatíveis!

3. **Compile o projeto**

**Windows (PowerShell/CMD):**
```powershell
cd src
javac -cp ".;..\lib\commons-math3-3.6.1.jar" *.java
```

**Linux/Mac:**
```bash
cd src
javac -cp ".:../lib/commons-math3-3.6.1.jar" *.java
```

> **Nota:** O separador do classpath é `;` no Windows e `:` no Linux/Mac

---

## 🚀 Como Usar

### Modo Interativo (Menu)

Execute sem argumentos para aceder ao menu principal:

**Windows:**
```powershell
cd src
java -cp ".;..\lib\commons-math3-3.6.1.jar" Main
```

**Linux/Mac:**
```bash
cd src
java -cp ".:../lib/commons-math3-3.6.1.jar" Main
```
O programa apresenta um menu interativo:

```
======================================
███╗   ███╗███████╗███╗   ██╗██╗   ██╗
████╗ ████║██╔════╝████╗  ██║██║   ██║
██╔████╔██║█████╗  ██╔██╗ ██║██║   ██║
██║╚██╔╝██║██╔══╝  ██║╚██╗██║██║   ██║
██║ ╚═╝ ██║███████╗██║ ╚████║╚██████╔╝
╚═╝     ╚═╝╚══════╝╚═╝  ╚═══╝ ╚═════╝ 
======================================

1. Carregar matriz e imprimi-la
2. Verificar estabilidade e estabilizar
3. Adicionar tarefas (A ⊕ B)
4. Testar matriz recorrente
5. Verificar elemento neutro
6. Nº recorrentes (sem Laplaciano)
7. Nº recorrentes (com Laplaciano reduzido)
8. Inversa estabilizada
9. Autovalores e autovetores
10. Autovalores e autovetores (com Fórmula Fechada)
0. Sair

Escolha uma opção:
```

Navegue pelo menu e escolha a funcionalidade desejada (0-10). O programa pedirá inputs conforme necessário.

### Modo Não-Interativo (Linha de Comandos)

Execute funcionalidades diretamente via argumentos:

```bash
# Sintaxe geral
java Main -f <funcionalidade> -a <ficheiro_A.csv> -o <output.txt> [opções]

# Exemplos:
# Funcionalidade 1: Carregar e imprimir matriz
java Main -f 1 -a Input/matriz3.csv -o Output/resultado.txt

# Funcionalidade 2: Estabilizar matriz
java Main -f 2 -a Input/matriz4.csv -o Output/resultado.txt -csv Output/estabilizada.csv

# Funcionalidade 3: Adicionar tarefas A ⊕ B
java Main -f 3 -a Input/matrizA.csv -b Input/matrizB.csv -o Output/resultado.txt

# Funcionalidade 4: Testar recorrência
java Main -f 4 -a Input/matriz3.csv -o Output/resultado.txt

# Funcionalidade 6: Contar recorrentes para dimensão 3
java Main -f 6 -d 3 -o Output/resultado.txt

# Funcionalidade 7: Contar recorrentes com Laplaciano
java Main -f 7 -d 4 -o Output/resultado.txt

# Funcionalidade 8: Inversa estabilizada
java Main -f 8 -a Input/matrizA.csv -e Input/elementoNeutro.csv -o Output/resultado.txt
```

**Parâmetros:**
- `-f` : Número da funcionalidade (1-10) [obrigatório]
- `-o` : Ficheiro de output TXT [obrigatório]
- `-a` : Ficheiro CSV da matriz A [conforme funcionalidade]
- `-b` : Ficheiro CSV da matriz B [funcionalidade 3]
- `-e` : Ficheiro CSV do elemento neutro [funcionalidade 8]
- `-d` : Dimensão n da matriz [funcionalidades 6, 7, 9, 10]
- `-csv` : Ficheiro CSV de output [opcional, funcionalidade 2]

---

## 📂 Formato de Ficheiros

### Input (CSV)

Os ficheiros devem estar em `Input/` no formato:
```csv
1,2,0
3,1,2
0,2,1
```
- Matriz quadrada n×n
- Valores inteiros ≥ 0
- Sem espaços

### Output

- **TXT:** Resultados detalhados em `Output/*.txt`
- **CSV:** Matrizes estabilizadas em `Output/*.csv`
- **JPG:** Snapshots visuais em `Output/snapshot_XX.jpg`

**Cores do Heatmap:**
- Branco = 0 | Amarelo = 1 | Castanho = 2 | Vermelho = 3 | Azul = ≥4

---

## 📂 Estrutura

```
lapr1-2526-DI03-Repo/
├── src/
│   ├── Main.java
│   ├── naoInterativo.java
│   ├── HeatmapImageWriter.java
│   └── TestesUnitarios.java
├── Input/                  # Matrizes CSV
├── Output/                 # Resultados
└── README.md
```

---

## 👥 Equipa BitMasters

**Grupo DI03 - LAPR1 2025/2026**

| Nome | Estudante Nº |
|------|--------------|
| Francisco Gomes       | 1250944 |
| Paulo Moreira         | 1251334 |
| Ricardo Gonçalves     | 1251384 |
| Rodrigo Queirós       | 1251425 |
| André Almeida         | 1240732 |

**ISEP - Engenharia Informática**

---

<div align="center">

**Made with ❤️ by BitMasters**

</div>
