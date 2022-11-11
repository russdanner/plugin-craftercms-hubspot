import * as React from 'react';
import ToolsPanelListItemButton from '@craftercms/studio-ui/components/ToolsPanelListItemButton';
import { showWidgetDialog } from '@craftercms/studio-ui/state/actions/dialogs';
import { useDispatch } from 'react-redux';

export function OpenHubspotDialogPanelButton(props) {
  const boardLabel = props.title ? props.title : 'Hubspot Tools';
  const boardIcon = props.icon && props.icon.id ? props.icon.id : '@mui/icons-material/ContentPasteOutlined';

  const dispatch = useDispatch();
  return (
    <ToolsPanelListItemButton
      icon={{ id: boardIcon }}
      title={boardLabel}
      onClick={() =>
        alert("Nothing to do")
        // dispatch(
        //   showWidgetDialog({
        //     title: boardLabel,
        //     extraProps: props,
        //     widget: {
        //       id: 'org.rd.plugin.trellowf.board'
        //     }
        //   })
        //)
      }
    />
  );
}

export default OpenHubspotDialogPanelButton;
