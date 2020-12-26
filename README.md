# Ukrainian Cases

The ukrainian language has 7 cases, and it mostly works changing the ending of the word
in nominative.  
The idea of this program is that sending an ukrainian word, you get back all the 
different cases, including plural.

## Why?

I am learning ukrainian and clojure, so it is nice to mix some grammar with LISP.

## Usage

Visit https://ukrainiannouns.herokuapp.com/ to try it.

## API
For now there are 2 main functions, you can try the API here:  
https://ukrainian-cases-api.herokuapp.com  

* https://ukrainian-cases-api.herokuapp.com/nouns/<ukrainian_noun>/gender
* https://ukrainian-cases-api.herokuapp.com/nouns/<ukrainian_noun>/nominative  

The API is also build with clojure using Compojure  
https://github.com/weavejester/compojure  


To use clojure follow these tutorials:  
https://clojure.org/guides/getting_started  
https://leiningen.org

## Examples

Look at the tests

## License

Copyright © 2020 Luis Godoy

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
