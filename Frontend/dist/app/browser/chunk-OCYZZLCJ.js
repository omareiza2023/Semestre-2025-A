import {
  __async
} from "./chunk-UL2P3LPA.js";

// node_modules/@ionic/core/dist/esm/lock-controller-316928be.js
var createLockController = () => {
  let waitPromise;
  const lock = () => __async(null, null, function* () {
    const p = waitPromise;
    let resolve;
    waitPromise = new Promise((r) => resolve = r);
    if (p !== void 0) {
      yield p;
    }
    return resolve;
  });
  return {
    lock
  };
};

export {
  createLockController
};
/*! Bundled license information:

@ionic/core/dist/esm/lock-controller-316928be.js:
  (*!
   * (C) Ionic http://ionicframework.com - MIT License
   *)
*/
//# sourceMappingURL=chunk-OCYZZLCJ.js.map
