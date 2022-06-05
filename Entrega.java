import java.lang.AssertionError;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.Set;

/*
 * Aquesta entrega consisteix en implementar tots els mÃ¨todes annotats amb el comentari "// TO DO".
 *
 * Cada tema tÃ© el mateix pes, i l'avaluaciÃ³ consistirÃ  en:
 *
 * - Principalment, el correcte funcionament de cada mÃ¨tode (provant amb diferents entrades). Teniu
 *   alguns exemples al mÃ¨tode `main`.
 *
 * - La neteja del codi (pensau-ho com faltes d'ortografia). L'estÃ ndar que heu de seguir Ã©s la guia
 *   d'estil de Google per Java: https://google.github.io/styleguide/javaguide.html . No Ã©s
 *   necessari seguir-la estrictament, perÃ² ens basarem en ella per jutjar si qualcuna se'n desvia
 *   molt.
 *
 * Per com estÃ  plantejada aquesta entrega, no necessitau (ni podeu) utilitzar cap `import`
 * addicional, ni mÃ¨todes de classes que no estiguin ja importades. El que sÃ­ podeu fer Ã©s definir
 * tots els mÃ¨todes addicionals que volgueu (de manera ordenada i dins el tema que pertoqui).
 *
 * Podeu fer aquesta entrega en grups de com a mÃ xim 3 persones, i necessitareu com a minim Java 8.
 * Per entregar, posau a continuaciÃ³ els vostres noms i entregau Ãºnicament aquest fitxer.
 * - Nom 1: Santiago Rattenbach Paliza-BartolomÃ©
 * - Nom 2: Albert Salom Vanrell
 * - Nom 3:
 *
 * L'entrega es farÃ  a travÃ©s d'una tasca a l'Aula Digital abans de la data que se us hagui
 * comunicat i vos recomanam que treballeu amb un fork d'aquest repositori per seguir mÃ©s fÃ cilment
 * les actualitzacions amb enunciats nous. Si no podeu visualitzar bÃ© algun enunciat, assegurau-vos
 * que el vostre editor de texte estigui configurat amb codificaciÃ³ UTF-8.
 */
class Entrega {
  /*
   * AquÃ­ teniu els exercicis del Tema 1 (LÃ²gica).
   *
   * Els mÃ¨todes reben de parÃ metre l'univers (representat com un array) i els predicats adients
   * (per exemple, `Predicate<Integer> p`). Per avaluar aquest predicat, si `x` Ã©s un element de
   * l'univers, podeu fer-ho com `p.test(x)`, tÃ© com resultat un booleÃ . Els predicats de dues
   * variables sÃ³n de tipus `BiPredicate<Integer, Integer>` i similarment s'avaluen com
   * `p.test(x, y)`.
   *
   * En cada un d'aquests exercicis us demanam que donat l'univers i els predicats retorneu `true`
   * o `false` segons si la proposiciÃ³ donada Ã©s certa (suposau que l'univers Ã©s suficientment
   * petit com per utilitzar la forÃ§a bruta)
   */
  static class Tema1 {
    /*
     * Ã‰s cert que ?x,y. P(x,y) -> Q(x) ^ R(y) ?
     */
    static boolean exercici1(
        int[] universe,
        BiPredicate<Integer, Integer> p,
        Predicate<Integer> q,
        Predicate<Integer> r) {
        for(int x:universe) {
            for (int y:universe) {
                if(p.test(x,y)) {
                    if(!(q.test(x)&&r.test(y))) {
                        return false;
                    }  
                }  
            }
        }
      return true;
    }

    /*
     * Ã‰s cert que ?!x. ?y. Q(y) -> P(x) ?
     */
    static boolean exercici2(int[] universe, Predicate<Integer> p, Predicate<Integer> q) {
        int condicionesNoCumplidas,xcorrectos=0;
        for(int x:universe) {
            condicionesNoCumplidas=0;
            for (int y:universe) {
                if(q.test(y)) {
                    if(!p.test(x)) {
                        condicionesNoCumplidas++;
                    }  
                }  
            }
            if(condicionesNoCumplidas==0) {
                xcorrectos++;           
            }
        }
        return xcorrectos==1;
    }

    /*
     * És cert que �(?x. ?y. y ? x) ?
     * ?x: ?y. �(y ? x)
     * Observau que els membres de l'univers sÃ³n arrays, tractau-los com conjunts i podeu suposar
     * que cada un d'ells estÃ  ordenat de menor a major.
     */
    static boolean exercici3(int[][] universe) {
        boolean existeX = false;
        for (int[] y : universe) {
            for (int[] x : universe) {
                if (x.length < y.length) {
                    existeX = false;
                } else {
                    int pertenecenA_X = 0;
                    for (int yn:y) {
                        boolean tieneX=false;
                        for (int xn:x){
                            if (!tieneX && (yn == xn)) {
                                pertenecenA_X++;
                                tieneX=true;
                            }
                        }
                        if (!existeX) {
                            existeX = (pertenecenA_X == y.length);
                        }
                    }
                }
            }
            if (!existeX) {
                return true;
            }
        }
        return false;
    }

    /*
     * Ã‰s cert que ?x. ?!y. x�y ? 1 (mod n) ?
     */
    static boolean exercici4(int[] universe, int n) {
        for (int x:universe){
            boolean tieneUnaY=false;
            for(int y:universe){
                if((x*y)%n==1){
                    if(tieneUnaY){
                        return false;
                    }
                    tieneUnaY=true;        
                }
            }
            if(!tieneUnaY){
                return false;
            }  
        }
        return true;
    }

    /*
     * AquÃ­ teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      // Exercici 1
      // âˆ€x,y. P(x,y) -> Q(x) ^ R(y)

      assertThat(
          exercici1(
              new int[] { 2, 3, 5, 6 },
              (x, y) -> x * y <= 4,
              x -> x <= 3,
              x -> x <= 3
          )
      );

      assertThat(
          !exercici1(
              new int[] { -2, -1, 0, 1, 2, 3 },
              (x, y) -> x * y >= 0,
              x -> x >= 0,
              x -> x >= 0
          )
      );

      // Exercici 2
      // âˆƒ!x. âˆ€y. Q(y) -> P(x) ?

      assertThat(
          exercici2(
              new int[] { -1, 1, 2, 3, 4 },
              x -> x < 0,
              x -> true
          )
      );

      assertThat(
          !exercici2(
              new int[] { 1, 2, 3, 4, 5, 6 },
              x -> x % 2 == 0, // x Ã©s mÃºltiple de 2
              x -> x % 4 == 0  // x Ã©s mÃºltiple de 4
          )
      );

      // Exercici 3
      // Â¬(âˆƒx. âˆ€y. y âŠ† x) ?

      assertThat(
          exercici3(new int[][] { {1, 2}, {0, 3}, {1, 2, 3}, {} })
      );

      assertThat(
          !exercici3(new int[][] { {1, 2}, {0, 3}, {1, 2, 3}, {}, {0, 1, 2, 3} })
      );

      // Exercici 4
      // Ã‰s cert que âˆ€x. âˆƒ!y. xÂ·y â‰¡ 1 (mod n) ?

      assertThat(
          exercici4(
              new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 },
              11
          )
      );

      assertThat(
          !exercici4(
              new int[] { 0, 5, 7 },
              13
          )
      );
    }
  }

  /*
   * AquÃ­ teniu els exercicis del Tema 2 (Conjunts).
   *
   * De la mateixa manera que al Tema 1, per senzillesa tractarem els conjunts com arrays (sense
   * elements repetits). Per tant, un conjunt de conjunts d'enters tendrÃ  tipus int[][].
   *
   * Les relacions tambÃ© les representarem com arrays de dues dimensions, on la segona dimensiÃ³
   * nomÃ©s tÃ© dos elements. Per exemple
   *   int[][] rel = {{0,0}, {1,1}, {0,1}, {2,2}};
   * i tambÃ© donarem el conjunt on estÃ  definida, per exemple
   *   int[] a = {0,1,2};
   *
   * Les funcions f : A -> B (on A i B son subconjunts dels enters) les representam donant int[] a,
   * int[] b, i un objecte de tipus Function<Integer, Integer> que podeu avaluar com f.apply(x) (on
   * x Ã©s un enter d'a i el resultat f.apply(x) Ã©s un enter de b).
   */
  static class Tema2 {
    /*
     * Ã‰s `p` una particiÃ³ d'`a`?
     *
     * `p` Ã©s un array de conjunts, haureu de comprovar que siguin elements d'`a`. Podeu suposar que
     * tant `a` com cada un dels elements de `p` estÃ  ordenat de menor a major.
     */
    static boolean exercici1(int[] a, int[][] p) {
        boolean [] enUso=new boolean[a.length];
        for(int [] pn:p){
            boolean [] enUsoParticion=new boolean[pn.length];
            for(int an=0;an<a.length;an++){
                for(int pni=0;pni<pn.length;pni++){
                    if (pn[pni]==a[an]&&!enUso[an]){
                        enUso[an]=true;
                        enUsoParticion[pni]=true;
                    }
                } 
            }
            for(int pni=0;pni<pn.length;pni++){
                if(!enUsoParticion[pni]){
                    return false;
                }
            }
        }
        return true;
    }

    /*
     * Comprovau si la relaciÃ³ `rel` definida sobre `a` Ã©s un ordre parcial i que `x` n'Ã©s el mÃ­nim.
     *
     * Podeu soposar que `x` pertany a `a` i que `a` estÃ  ordenat de menor a major.
     */
    static boolean exercici2(int[] a, int[][] rel, int x) {
        boolean esRef,esAntisi,esTrans=false;
        boolean [] cumpleCondicion;
        int relacionesX=0;
        
        for(int [] reln:rel){
            if(x==reln[0]){
                relacionesX++;
            }
        }
        if(relacionesX!=a.length){
            return false;
        }
        
        cumpleCondicion=new boolean[a.length];
        for(int an=0;an<a.length;an++){    
            for(int [] reln:rel){
                if(a[an]==reln[0]&&a[an]==reln[1]){
                    cumpleCondicion[an]=true;
                }
            }
        }
        for(int an=0;an<a.length;an++){ 
            if (!cumpleCondicion[an]){
                return false;
            }
        }
        esRef=true;
        
        for(int an=0;an<a.length;an++){    
            for(int [] reln1:rel){
                int x1=reln1[0],y1=reln1[1];
                for(int [] reln2:rel){
                    int x2=reln2[0],y2=reln2[1];
                    if(x1==y2&&x2==y1){
                        if(!(x1==y1)){
                            return false;
                        }
                    }
                }
            }
        }
        esAntisi=true;
        
        for(int an=0;an<a.length;an++){    
            for(int [] reln1:rel){
                int x1=reln1[0],y1=reln1[1];
                for(int [] reln2:rel){
                    int x2=reln2[0],y2=reln2[1];
                    if(y1==x2&&x1!=y2){
                        esTrans=false;
                        for(int [] reln3:rel){
                            int x3=reln3[0],y3=reln3[1];
                            if(x3==x1&&y3==y2){
                                esTrans=true;
                            }
                        }
                        if(!esTrans){
                            return false;
                        }
                    }
                }
            }    
        }
        
        return true;
    }

    /*
     * Suposau que `f` Ã©s una funciÃ³ amb domini `dom` i codomini `codom`.  Trobau l'antiimatge de
     * `y` (ordenau el resultat de menor a major, podeu utilitzar `Arrays.sort()`). Podeu suposar
     * que `y` pertany a `codom` i que tant `dom` com `codom` tambÃ© estÃ n ordenats de menor a major.
     */
    static int[] exercici3(int[] dom, int[] codom, Function<Integer, Integer> f, int y) {
        int indiceCumplen=0;
        int [] numsSeCumplen=new int[dom.length];
        for(int x:dom){
            if(f.apply(x)==y){
                numsSeCumplen[indiceCumplen]=x;
                indiceCumplen++;
                
            }
        }
        int [] antiImagen=new int[indiceCumplen];
        for(int indice=0;indice<indiceCumplen;indice++){
            antiImagen[indice]=numsSeCumplen[indice];
        }
        Arrays.sort(antiImagen);
        
        return antiImagen; // TO DO
    }

    /*
     * Suposau que `f` Ã©s una funciÃ³ amb domini `dom` i codomini `codom`.  Retornau:
     * - 3 si `f` Ã©s bijectiva
     * - 2 si `f` nomÃ©s Ã©s exhaustiva
     * - 1 si `f` nomÃ©s Ã©s injectiva
     * - 0 en qualsevol altre cas
     *
     * Podeu suposar que `dom` i `codom` estÃ n ordenats de menor a major. Per comoditat, podeu
     * utilitzar les constants definides a continuaciÃ³:
     */
    static final int NOTHING_SPECIAL = 0;
    static final int INJECTIVE = 1;
    static final int SURJECTIVE = 2;
    static final int BIJECTIVE = INJECTIVE + SURJECTIVE;

    static int exercici4(int[] dom, int[] codom, Function<Integer, Integer> f) {
        int [] numAntiImagen=new int[codom.length];
        boolean esInyectiva=true,esExhaustiva=true;
        for(int y=0;y<codom.length;y++){
            for(int x:dom){
                if(f.apply(x)==codom[y]){
                    numAntiImagen[y]++;
                }
                if(numAntiImagen[y]>1){
                    esInyectiva=false;
                }
            }
            if(numAntiImagen[y]==0){
                esExhaustiva=false;
            }
        }
        if(esInyectiva&&!esExhaustiva){
            return INJECTIVE;
        }else if(!esInyectiva&&esExhaustiva){
            return SURJECTIVE;
        }else if(esInyectiva&&esExhaustiva) {
            return BIJECTIVE;
        }else{
            return NOTHING_SPECIAL;
        }
    }

    /*
     * AquÃ­ teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      // Exercici 1
      // `p` Ã©s una particiÃ³ d'`a`?

      assertThat(
          exercici1(
              new int[] { 1, 2, 3, 4, 5 },
              new int[][] { {1, 2}, {3, 5}, {4} }
          )
      );

      assertThat(
          !exercici1(
              new int[] { 1, 2, 3, 4, 5 },
              new int[][] { {1, 2}, {5}, {1, 4} }
          )
      );

      // Exercici 2
      // Ã©s `rel` definida sobre `a` d'ordre parcial i `x` n'Ã©s el mÃ­nim?

      ArrayList<int[]> divisibility = new ArrayList<int[]>();

      for (int i = 1; i < 8; i++) {
        for (int j = 1; j <= i; j++) {
          if (i % j == 0) {
            // i Ã©s mÃºltiple de j, Ã©s a dir, j|i
            divisibility.add(new int[] { j, i });
          }
        }
      }

      assertThat(
          exercici2(
              new int[] { 1, 2, 3, 4, 5, 6, 7 },
              divisibility.toArray(new int[][] {}), //{(1,2),(1,3),(2,4),(2,6),(3,6),(4,8)}
              1
          )
      );

      assertThat(
          !exercici2(
              new int[] { 1, 2, 3 },
              new int[][] { {1, 1}, {2, 2}, {3, 3}, {1, 2}, {2, 3} },
              1
          )
      );

      assertThat(
          !exercici2(
              new int[] { 1, 2, 3, 4, 5, 6, 7 },
              divisibility.toArray(new int[][] {}),
              2
          )
      );

      // Exercici 3
      // calcular l'antiimatge de `y`

      assertThat(
          Arrays.equals(
              new int[] { 0, 2 },
              exercici3(
                  new int[] { 0, 1, 2, 3 },
                  new int[] { 0, 1 },
                  x -> x % 2, // residu de dividir entre 2
                  0
              )
          )
      );

      assertThat(
          Arrays.equals(
              new int[] { },
              exercici3(
                  new int[] { 0, 1, 2, 3 },
                  new int[] { 0, 1, 2, 3, 4 },
                  x -> x + 1,
                  0
              )
          )
      );

      // Exercici 4
      // classificar la funciÃ³ en res/injectiva/exhaustiva/bijectiva

      assertThat(
          exercici4(
              new int[] { 0, 1, 2, 3 },
              new int[] { 0, 1, 2, 3 },
              x -> (x + 1) % 4
          )
          == BIJECTIVE
      );

      assertThat(
          exercici4(
              new int[] { 0, 1, 2, 3 },
              new int[] { 0, 1, 2, 3, 4 },
              x -> x + 1
          )
          == INJECTIVE
      );

      assertThat(
          exercici4(
              new int[] { 0, 1, 2, 3 },
              new int[] { 0, 1 },
              x -> x / 2
          )
          == SURJECTIVE
      );

      assertThat(
          exercici4(
              new int[] { 0, 1, 2, 3 },
              new int[] { 0, 1, 2, 3 },
              x -> x <= 1 ? x+1 : x-1
          )
          == NOTHING_SPECIAL
      );
    }
  }

  /*
   * AquÃ­ teniu els exercicis del Tema 3 (AritmÃ¨tica).
   *
   */
  static class Tema3 {
    /*
     * Donat `a`, `b` retornau el mÃ xim comÃº divisor entre `a` i `b`.
     *
     * Podeu suposar que `a` i `b` sÃ³n positius.
     */
    static int exercici1(int a, int b) {
        int divisor, dividendo;
        if (a>b) {
            divisor=b;
            dividendo=a;
        }
        else {
            divisor=a;
            dividendo=b;
        }
        int resto=dividendo%divisor;
        
        while(resto!=0) {
            dividendo=divisor;
            divisor=resto;
            resto=dividendo%divisor;
        } 
        return divisor; // TO DO
    }

    /*
     * Es cert que `a``x` + `b``y` = `c` tÃ© soluciÃ³?.
     *
     * Podeu suposar que `a`, `b` i `c` sÃ³n positius.
     */
    static boolean exercici2(int a, int b, int c) {
        int mcd=exercici1(a,b);
        return (c%mcd==0); // TO DO
    }

    /*
     * Quin es l'invers de `a` mÃ²dul `n`?
     *
     * Retornau l'invers sempre entre 1 i `n-1`, en cas que no existeixi retornau -1
     */
    static int exercici3(int a, int n) {
        int resultado;
        if (exercici1(a,n)!=1) {
            return -1; 
        }
        for (int x=0;x<n;x++) {
            resultado=((a*x)%n);
            if (resultado==1) {
                return x;
            }
        }
        return -1; // TO DO
    }

    /*
     * AquÃ­ teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      // Exercici 1
      // `mcd(a,b)`

      assertThat(
              exercici1(2, 4) == 2
      );

      assertThat(
              exercici1(1236, 984) == 12
      );

      // Exercici 2
      // `a``x` + `b``y` = `c` tÃ© soluciÃ³?

      assertThat(
              exercici2(4,2,2)
      );
      assertThat(
              !exercici2(6,2,1)
      );
      // Exercici 3
      // invers de `a` mÃ²dul `n`
      assertThat(exercici3(2, 5) == 3);
      assertThat(exercici3(2, 6) == -1);
    }
  }
  
  static class Tema4 {
    /*
     * Donada una matriu d'adjacencia `A` d'un graf no dirigit, retornau l'ordre i la mida del graf.
     */
    static int[] exercici1(int[][] A) {
        int ordre=0;
        int mida=0;
        for (int [] v:A) {
            ordre++;
            for (int vi:v) {
                mida+=vi;
            }
        }
        mida/=2;
      return new int[]{ordre, mida}; // TO DO
    }

    /*
     * Donada una matriu d'adjacencia `A` d'un graf no dirigit, digau si el graf es euleri�.
     */
    static boolean exercici2(int[][] A) {
        for (int [] v:A) {
            int aristas=0;
            for (int vi:v) {
                aristas+=vi;
            }
            if (aristas%2==1) {
                return false;
            }
        }
      return true; // TO DO
    }

    /*
     * Donat `n` el n�mero de fulles d'un arbre arrelat i `d` el nombre de fills dels nodes interiors,
     * retornau el nombre total de v�rtexos de l'arbre
     *
     */
    static int exercici3(int n, int d) {
      //dado n el numero de fulles de un arbol y d el numero de hijos de los nodos interiores devuelve el numero total de vertices del arbol
           return 777; 
    }

    /*
     * Donada una matriu d'adjacencia `A` d'un graf connex no dirigit, digau si el graf cont� alg�n cicle.
     */
    static boolean exercici4(int[][] A) {
        for (int [] v:A) {
            for (int vi:v) {
                
            }
        }
      return false; // TO DO
    }
    /*
     * Aqu� teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      // Exercici 1
      // `ordre i mida`

      assertThat(
              Arrays.equals(exercici1(new int[][] { {0, 1, 0}, {1, 0, 1}, {0,1, 0}}), new int[] {3, 2})
      );

      assertThat(
              Arrays.equals(exercici1(new int[][] { {0, 1, 0, 1}, {1, 0, 1, 1}, {0 , 1, 0, 1}, {1, 1, 1, 0}}), new int[] {4, 5})
      );

      // Exercici 2
      // `Es euleri�?`

      assertThat(
              exercici2(new int[][] { {0, 1, 1}, {1, 0, 1}, {1, 1, 0}})
      );
      assertThat(
              !exercici2(new int[][] { {0, 1, 0}, {1, 0, 1}, {0,1, 0}})
      );
      // Exercici 3
      // `Quants de nodes t� l'arbre?`
      assertThat(exercici3(5, 2) == 9);
      assertThat(exercici3(7, 3) == 10);

      // Exercici 4
      // `Cont� alg�n cicle?`
      assertThat(
              exercici4(new int[][] { {0, 1, 1}, {1, 0, 1}, {1, 1, 0}})
      );
      assertThat(
              !exercici4(new int[][] { {0, 1, 0}, {1, 0, 1}, {0, 1, 0}})
      );

    }
  }

  /*
   * Aquest mÃ¨tode `main` contÃ© alguns exemples de parÃ metres i dels resultats que haurien de donar
   * els exercicis. Podeu utilitzar-los de guia i tambÃ© en podeu afegir d'altres (no els tendrem en
   * compte, perÃ² Ã©s molt recomanable).
   *
   * Podeu aprofitar el mÃ¨tode `assertThat` per comprovar fÃ cilment que un valor sigui `true`.
   */
  public static void main(String[] args) {
    Tema1.tests();
    Tema2.tests();
    Tema3.tests();
    Tema4.tests();
  }

  static void assertThat(boolean b) {
    if (!b)
      throw new AssertionError();
  }
}

// vim: set textwidth=100 shiftwidth=2 expandtab :
