# 📚 Scientific Paper Analyzer - Sistema de Gestión de Artículos Científicos

## 📋 Descripción del Proyecto

**Scientific Paper Analyzer** es un sistema de gestión de resúmenes científicos desarrollado en Java que permite organizar, buscar y analizar artículos académicos mediante estructuras de datos eficientes. El sistema implementa una HashTable personalizada (O(1)) y árboles AVL (O(log n)) desde cero, sin utilizar las colecciones estándar de Java.

### ✨ Características Principales

- 📄 **Gestión de Resúmenes**: Agregar y almacenar artículos científicos
- 🔍 **Búsqueda Eficiente**: Por título (O(1)), autor (O(log n)) y palabra clave (O(log n))
- 📊 **Análisis de Frecuencias**: Estadísticas de palabras clave en los resúmenes
- 🗂️ **Organización Inteligente**: Triple indexación para acceso rápido
- 🌐 **Interfaz Gráfica**: GUI amigable desarrollada con Swing

---

## 🎓 Contexto Académico

**Universidad:** Universidad Metropolitana, Caracas, Venezuela  
**Asignatura:** Estructuras de Datos (BPTSP06)  
**Proyecto:** Proyecto 2 - Sistema de Administración de Artículos Científicos  
**Fecha:** Noviembre 2025

### 📚 Requisitos Cumplidos

1. ✅ Implementación de HashTable con función hash personalizada (FNV-1a)
2. ✅ Implementación de Árbol AVL con rotaciones y auto-balanceo
3. ✅ Sistema de almacenamiento y recuperación de resúmenes
4. ✅ Búsqueda por título, autor y palabra clave
5. ✅ Análisis estadístico de palabras clave
6. ✅ Interfaz gráfica con Swing
7. ✅ Documentación Javadoc completa
8. ✅ Diagrama UML de arquitectura

---

## 🏗️ Arquitectura del Sistema

### Estructura en Capas

```
┌─────────────────────────────────────┐
│     UI Layer (MainFrame)            │  ← Interfaz gráfica Swing
├─────────────────────────────────────┤
│  Services Layer (4 Services)        │  ← Lógica de negocio
├─────────────────────────────────────┤
│   Repository Layer (Repository)     │  ← Coordinador de datos
├─────────────────────────────────────┤
│  Data Structures (HashTable + AVL)  │  ← Estructuras propias
└─────────────────────────────────────┘
```

### Triple Indexación

El sistema utiliza tres estructuras para optimizar las búsquedas:

| Estructura | Uso | Complejidad |
|------------|-----|-------------|
| **HashTable** | Títulos de resúmenes | O(1) |
| **AVL Tree** | Índice de autores | O(log n) |
| **AVL Tree** | Índice de palabras clave | O(log n) |

---

## 🚀 Instalación y Ejecución

### Requisitos Previos

- Java JDK 11 o superior
- NetBeans IDE (recomendado) o cualquier IDE compatible con Maven

### Repositorio


git clone https://github.com/rafa3127/ScientificPaperAnalyzer
cd ScientificPaperAnalyzer

### Estructura de Datos

El sistema busca archivos `.txt` en el directorio `data`. Crear esta carpeta si no existe:

```bash
mkdir -p data
```

---

## 📖 Manual de Usuario

### Pantalla Principal

Al iniciar la aplicación, verás 5 pestañas principales:

```
┌──────────────────────────────────────────────────┐
│ [Agregar] [Analizar] [Buscar Keyword] [Buscar Autor] [Listar Keywords] │
└──────────────────────────────────────────────────┘
```

---

### 1️⃣ Panel: Agregar Resumen

**Función:** Importar nuevos artículos científicos al sistema.

#### Cómo usar:

1. Click en **"📂 Seleccionar Archivo .txt"**
2. Navega y selecciona un archivo de resumen
3. El sistema mostrará una **vista previa** con:
   - 📄 Título
   - 👥 Autores
   - 🔑 Palabras clave
   - 📝 Resumen (primeros 300 caracteres)
4. Si el resumen ya existe, recibirás una advertencia
5. Click en **"✅ Confirmar y Agregar"** para importar

#### Formato de Archivo Requerido:

```
Título del Artículo
Autores
Nombre Autor 1
Nombre Autor 2
Resumen
Texto del resumen del artículo científico...
Palabras claves: palabra1, palabra2, palabra3.
```

---

### 2️⃣ Panel: Analizar Resumen

**Función:** Ver detalles completos de un artículo y análisis de frecuencia de palabras clave.

#### Cómo usar:

1. Selecciona un resumen del **menú desplegable** (ordenados alfabéticamente)
2. Click en **"🔍 Analizar Resumen"**
3. Se mostrará:
   - 📄 **Título completo**
   - 👥 **Lista de autores** (numerada)
   - 📝 **Texto completo del resumen**
   - 🔑 **Palabras clave del paper**
   - 📈 **Tabla de frecuencias** con:
     - Palabra clave
     - Frecuencia (veces que aparece)
     - Porcentaje (del total de ocurrencias)

#### Ejemplo de Análisis:

```
Palabra Clave               Frecuencia    Porcentaje
─────────────────────────────────────────────────────
inteligencia artificial           5         35.71%
machine learning                  4         28.57%
redes neuronales                  3         21.43%
...
```

---

### 3️⃣ Panel: Buscar Keyword

**Función:** Encontrar todos los artículos que contienen una palabra clave específica.

#### Cómo usar:

1. Selecciona una palabra clave del **menú desplegable** (ordenadas alfabéticamente)
2. Click en **"🔍 Buscar Palabra Clave"**
3. Se mostrará una **tabla de resultados** con:
   - Títulos de las investigaciones encontradas
   - Contador de resultados: `📋 Investigaciones Encontradas: (X)`
4. **Haz doble click** en cualquier fila para ver detalles completos del artículo

#### Información en el Detalle:

- 📄 Título
- 👥 Autores
- 🔑 Palabras clave
- 📝 Resumen completo

---

### 4️⃣ Panel: Buscar Autor

**Función:** Encontrar todas las investigaciones de un autor específico.

#### Cómo usar:

1. Selecciona un autor del **menú desplegable** (ordenados alfabéticamente)
2. Click en **"🔍 Buscar Autor"**
3. Se mostrará una **tabla de resultados** con:
   - Títulos de las investigaciones del autor
   - Contador de resultados: `📋 Investigaciones Encontradas: (X)`
4. **Haz click** en cualquier fila para ver detalles completos del artículo

#### Nota:
- El sistema encuentra al autor tanto si es autor principal como coautor
- La búsqueda es **case-insensitive** y **accent-insensitive**

---

### 5️⃣ Panel: Listar Keywords

**Función:** Ver todas las palabras clave del sistema con estadísticas globales.

#### Cómo usar:

1. Al activar la pestaña, se carga automáticamente una **tabla** con:
   - **Palabra clave** (columna 1)
   - **Frecuencia Global** (columna 2) - número de papers que la contienen
2. El título muestra: `📊 Palabras Clave del Sistema: (X)`
3. **Haz click** en cualquier fila para ver:
   - 🔑 La palabra clave
   - 📈 Frecuencia global (en cuántos papers aparece)
   - 📚 **Lista completa de investigaciones** que contienen esta palabra

#### Ejemplo de Detalle:

```
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
🔑 PALABRA CLAVE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
machine learning

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
📈 FRECUENCIA GLOBAL
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
Esta palabra clave aparece en 3 investigación(es)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
📚 INVESTIGACIONES
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
1. Título del Paper 1
   Autores: Autor A, Autor B

2. Título del Paper 2
   Autores: Autor C, Autor D
...
```

---

## 🔧 Características Técnicas

### Estructuras de Datos Implementadas

#### 1. HashTable (Función Hash FNV-1a)
- **Complejidad:** O(1) promedio
- **Colisiones:** Separate chaining con listas enlazadas
- **Factor de carga:** 0.75 (redimensionamiento automático)
- **Uso:** Almacenamiento de resúmenes por título

#### 2. Árbol AVL
- **Complejidad:** O(log n) para insert, search, delete
- **Balance:** Factor de balance [-1, 0, 1]
- **Rotaciones:** LL, RR, LR, RL
- **Uso:** Índices de autores y palabras clave

#### 3. LinkedList
- **Complejidad:** O(n) búsqueda, O(1) inserción al final
- **Uso:** Almacenamiento de autores, keywords y resultados

### Normalización de Claves

El sistema utiliza **normalización NFD** para búsquedas:
- Conversión a minúsculas
- Eliminación de acentos (á → a)
- Conversión de ñ → n
- **Los nombres originales se preservan para visualización**

**Ejemplo:**
```
"José García" → normalizado: "jose garcia"
                mostrado: "José García"
```

### Algoritmos de Ordenamiento

**MergeSort** con Collator español:
- **Complejidad:** O(n log n)
- **Locale:** es_ES
- **Strength:** PRIMARY (ignora acentos y mayúsculas)

---

## 📊 Complejidades del Sistema

| Operación | Complejidad | Estructura |
|-----------|-------------|------------|
| Agregar resumen | O(log n) | AVL insert (x2) |
| Buscar por título | **O(1)** | HashTable |
| Buscar por autor | O(log n) | AVL search |
| Buscar por keyword | O(log n) | AVL search |
| Listar títulos ordenados | O(n log n) | MergeSort |
| Listar autores/keywords | O(n) | AVL inorder |
| Analizar frecuencias | O(k·m) | k=keywords, m=body |

---

## 📁 Estructura del Proyecto

```
ScientificPaperAnalyzer/
├── src/main/java/
│   ├── basicdatastructures/     # Estructuras de datos propias
│   │   ├── AVLTree.java         # Árbol AVL
│   │   ├── AVLTreeNode.java
│   │   ├── HashTable.java       # Tabla hash con FNV-1a
│   │   ├── Entry.java
│   │   ├── LinkedList.java
│   │   └── Node.java
│   ├── models/                  # Modelos de dominio
│   │   ├── Summary.java         # Modelo principal
│   │   ├── AnalysisResult.java  # DTO para análisis
│   │   └── KeywordDetails.java  # DTO para keywords
│   ├── io/                      # Entrada/Salida
│   │   ├── FileIO.java
│   │   └── SummaryParser.java
│   ├── repositories/            # Capa de datos
│   │   ├── Repository.java      # Coordinador central
│   │   └── SummaryRecord.java
│   ├── services/                # Lógica de negocio
│   │   ├── SummaryService.java
│   │   ├── AnalysisService.java
│   │   ├── SearchService.java
│   │   └── KeywordService.java
│   ├── ui/                      # Interfaz gráfica
│   │   └── MainFrame.java
│   ├── utils/                   # Utilidades
│   │   └── MergeSort.java
│   └── com/graph/scientificpaperanalyzer/
│       └── ScientificPaperAnalyzer.java  # Main
├── data/ # Archivos .txt de resúmenes
├── UML.md                       # Diagrama de clases
├── README.md                    # Este archivo
└── pom.xml                      # Configuración Maven
```

---

## 🎯 Requerimientos Técnicos

### Restricciones del Proyecto

❌ **No se permite usar:**
- `java.util.HashMap`
- `java.util.TreeMap`
- Otras estructuras de Java Collections para datos principales

✅ **Se debe implementar:**
- HashTable personalizada con función hash propia
- Árbol AVL con rotaciones manuales
- LinkedList propia

✅ **Se permite usar:**
- `java.util.*` para UI (Swing components, event handling)
- `java.io.*` para manejo de archivos
- `java.text.*` para Collator (ordenamiento español)

---

**Última actualización:** Noviembre 2025
