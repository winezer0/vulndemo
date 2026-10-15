class Service {}
declare const override: {provide: string};
declare const config: {providers: unknown[]};

class Module {
  static forRoot() {
    return {providers: [
      {provide: 'WRONG', useClass: Service, ...override},
      {...override, provide: 'RIGHT', useClass: Service},
    ]};
  }
  static forOther() {
    return {providers: [{provide: 'WRONG_OUTER', useClass: Service}], ...config};
  }
}
