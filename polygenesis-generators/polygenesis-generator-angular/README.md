# PolyGenesis Angular Generator

**PolyGenesis Angular Generator** is an opinionated generator for Angular applications.

> IMPORTANT: PolyGenesis Angular Generator is based on SCSS and Angular Material 


# **!!! WORK IN PROGRESS !!!**

## Getting Started

In order to use the PolyGenesis Angular Generator you should execute the following:


### Create new Angular project with Angular Cli

[Angular Cli](https://angular.io/cli) is the easiest way to create a new Angular Project. 

```bash
ng new --routing=true --style=scss your_project_name_here && cd your_project_name_here 
```

### Create new Angular project with alternative ways

If you use [Nrwl](https://nrwl.io/) or another way to create your project ... TODO ...




### Install dependencies

PolyGenesis Angular Generator depends on the following modules:

- [Angular Flex-Layout](https://github.com/angular/flex-layout) 
- [Angular Material](https://material.angular.io/)
- [Hammer.js](http://hammerjs.github.io/)
- [Ngrx: Reactive State for Angular ](https://ngrx.io/) 

#### NPM
```bash
npm install --save bootstrap
npm install --save @angular/flex-layout
npm install --save @angular/material @angular/cdk @angular/animations
npm install --save hammerjs
npm install --save-dev @types/hammerjs 
npm install --save @ngrx/store @ngrx/store-devtools @ngrx/entity @ngrx/effects
```


#### Yarn
```bash
yarn add bootstrap
yarn add @angular/flex-layout
yarn add @angular/material @angular/cdk @angular/animations
yarn add hammerjs
yarn add --dev @types/hammerjs 
yarn add @ngrx/store @ngrx/store-devtools @ngrx/entity @ngrx/effects
```

## First Time Execution

By default, the first time you execute PolyGenesis Angular Generator for your project, a bunch of files
will be created or updated with the required settings. Future executions will not affect these files,
so you can freely adjust them.

This should not be a problem for fresh projects. If you have an existing project though, you
might want to disable this feature and enter the changes manually.

To disable the first time execution code generation, TODO.

For existing apps, follow these steps to begin using PolyGenesis Angular Generator.

### Step 1: Configure animations

Once the animations package is installed, import BrowserAnimationsModule into your application to 
enable animations support.

```typescript
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

@NgModule({
  ...
  imports: [BrowserAnimationsModule],
  ...
})
export class AppModule { }
```

Alternatively, you can disable animations by importing NoopAnimationsModule.

```typescript
import {NoopAnimationsModule} from '@angular/platform-browser/animations';

@NgModule({
  ...
  imports: [NoopAnimationsModule],
  ...
})
export class AppModule { }
```

### Step 2: Import the component modules

Create a separate NgModule that imports all of the Angular Material components that you will use in 
your application. You can then include this module wherever you'd like to use the components.

```typescript
import {MatButtonModule, MatCheckboxModule} from '@angular/material';

@NgModule({
  imports: [MatButtonModule, MatCheckboxModule],
  exports: [MatButtonModule, MatCheckboxModule],
})
export class MyOwnCustomMaterialModule { }
```

Be sure to import the Angular Material modules after Angular's BrowserModule, as the import order 
matters for NgModules.



### Step 3: Include a theme

Including a theme is required to apply all of the core and theme styles to your application.

To get started with a prebuilt theme, include one of Angular Material's prebuilt themes globally 
in your application. If you're using the Angular CLI, you can add this to your styles.scss:

```scss
@import "~@angular/material/prebuilt-themes/indigo-pink.css";
```

If you are not using the Angular CLI, you can include a prebuilt theme via a <link> element in your index.html.

For more information on theming and instructions on how to create a custom theme, see the 
[theming guide](https://material.angular.io/guide/theming).



## References

* https://medium.com/@cyrilletuzi/architecture-in-angular-projects-242606567e40
* https://medium.com/@bojzi/anatomy-of-a-large-angular-application-f098e5e36994
* https://bulldogjob.com/articles/539-scalable-angular-application-architecture
* https://itnext.io/choosing-a-highly-scalable-folder-structure-in-angular-d987de65ec7
* https://itnext.io/ngrx-best-practices-for-enterprise-angular-applications-6f00bcdf36d7
* 
* https://medium.com/@tomastrajan
* https://blog.usejournal.com/best-practices-for-writing-angular-6-apps-e6d3c0f6c7c1
* https://blog.angular-university.io/angular-2-smart-components-vs-presentation-components-whats-the-difference-when-to-use-each-and-why/
* https://medium.freecodecamp.org/a-beginners-guide-to-redux-9f652cbdc519

