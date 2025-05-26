import {
  eye,
  eyeOff
} from "./chunk-Y2OY2WAF.js";
import {
  createColorClasses
} from "./chunk-WOV3UQHA.js";
import {
  getIonMode
} from "./chunk-N56DZ42E.js";
import {
  Host,
  getElement,
  h,
  registerInstance
} from "./chunk-5DHXCOKT.js";
import {
  printIonWarning
} from "./chunk-DFDJHPIB.js";
import "./chunk-UL2P3LPA.js";

// node_modules/@ionic/core/dist/esm/ion-input-password-toggle.entry.js
var iosInputPasswordToggleCss = "";
var IonInputPasswordToggleIosStyle0 = iosInputPasswordToggleCss;
var mdInputPasswordToggleCss = "";
var IonInputPasswordToggleMdStyle0 = mdInputPasswordToggleCss;
var InputPasswordToggle = class {
  constructor(hostRef) {
    registerInstance(this, hostRef);
    this.togglePasswordVisibility = () => {
      const {
        inputElRef
      } = this;
      if (!inputElRef) {
        return;
      }
      inputElRef.type = inputElRef.type === "text" ? "password" : "text";
    };
    this.color = void 0;
    this.showIcon = void 0;
    this.hideIcon = void 0;
    this.type = "password";
  }
  /**
   * Whenever the input type changes we need to re-run validation to ensure the password
   * toggle is being used with the correct input type. If the application changes the type
   * outside of this component we also need to re-render so the correct icon is shown.
   */
  onTypeChange(newValue) {
    if (newValue !== "text" && newValue !== "password") {
      printIonWarning(`[ion-input-password-toggle] - Only inputs of type "text" or "password" are supported. Input of type "${newValue}" is not compatible.`, this.el);
      return;
    }
  }
  connectedCallback() {
    const {
      el
    } = this;
    const inputElRef = this.inputElRef = el.closest("ion-input");
    if (!inputElRef) {
      printIonWarning("[ion-input-password-toggle] - No ancestor ion-input found. This component must be slotted inside of an ion-input.", el);
      return;
    }
    this.type = inputElRef.type;
  }
  disconnectedCallback() {
    this.inputElRef = null;
  }
  render() {
    var _a, _b;
    const {
      color,
      type
    } = this;
    const mode = getIonMode(this);
    const showPasswordIcon = (_a = this.showIcon) !== null && _a !== void 0 ? _a : eye;
    const hidePasswordIcon = (_b = this.hideIcon) !== null && _b !== void 0 ? _b : eyeOff;
    const isPasswordVisible = type === "text";
    return h(Host, {
      key: "1a28e078c83e74c72d8bb8189ece93ec2e3fa3d0",
      class: createColorClasses(color, {
        [mode]: true
      })
    }, h("ion-button", {
      key: "039d1bab764093bb6fe4a34299b0872abda087fd",
      mode,
      color,
      fill: "clear",
      shape: "round",
      "aria-checked": isPasswordVisible ? "true" : "false",
      "aria-label": "show password",
      role: "switch",
      type: "button",
      onPointerDown: (ev) => {
        ev.preventDefault();
      },
      onClick: this.togglePasswordVisibility
    }, h("ion-icon", {
      key: "26477ee97b808c3d79944bf5e33d4e05f1ae0b3f",
      slot: "icon-only",
      "aria-hidden": "true",
      icon: isPasswordVisible ? hidePasswordIcon : showPasswordIcon
    })));
  }
  get el() {
    return getElement(this);
  }
  static get watchers() {
    return {
      "type": ["onTypeChange"]
    };
  }
};
InputPasswordToggle.style = {
  ios: IonInputPasswordToggleIosStyle0,
  md: IonInputPasswordToggleMdStyle0
};
export {
  InputPasswordToggle as ion_input_password_toggle
};
/*! Bundled license information:

@ionic/core/dist/esm/ion-input-password-toggle.entry.js:
  (*!
   * (C) Ionic http://ionicframework.com - MIT License
   *)
*/
//# sourceMappingURL=chunk-C4PUDOLF.js.map
