import { PluginDescriptor } from '@craftercms/studio-ui';
import OpenHubspotDialogPanelButton from './components/OpenHubspotDialogPanelButton';

const plugin: PluginDescriptor = {
  locales: undefined,
  scripts: undefined,
  stylesheets: undefined,
  id: 'org.rd.plugin.hubspot',
  widgets: {
    'org.rd.plugin.hubspot.openHubspotButton': OpenHubspotDialogPanelButton,
//    'org.rd.plugin.trellowf.board': Board
  }
};

export { OpenHubspotDialogPanelButton };

export default plugin;
