/*
 * DBeaver - Universal Database Manager
 * Copyright (C) 2010-2017 Serge Rider (serge@jkiss.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jkiss.dbeaver.ui.editors;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.part.MultiPageEditorSite;
import org.jkiss.dbeaver.core.CoreCommands;
import org.jkiss.dbeaver.core.CoreMessages;
import org.jkiss.dbeaver.model.DBPContextProvider;
import org.jkiss.dbeaver.model.DBPDataSourceContainer;
import org.jkiss.dbeaver.model.IDataSourceContainerProvider;
import org.jkiss.dbeaver.model.edit.DBEObjectReorderer;
import org.jkiss.dbeaver.model.exec.DBCExecutionContext;
import org.jkiss.dbeaver.model.navigator.DBNDatabaseFolder;
import org.jkiss.dbeaver.model.navigator.DBNDatabaseNode;
import org.jkiss.dbeaver.model.navigator.DBNNode;
import org.jkiss.dbeaver.registry.editor.EntityEditorsRegistry;
import org.jkiss.dbeaver.ui.ActionUtils;
import org.jkiss.dbeaver.ui.DBeaverIcons;
import org.jkiss.dbeaver.ui.UIIcon;
import org.jkiss.dbeaver.ui.UIUtils;
import org.jkiss.dbeaver.ui.actions.navigator.NavigatorHandlerFilterConfig;
import org.jkiss.dbeaver.ui.editors.entity.EntityEditor;

import java.util.List;

/**
 * DB editor utils
 */
public class DatabaseEditorUtils {

    public static void setPartBackground(IEditorPart editor, Composite composite)
    {
        CTabFolder tabFolder = null;
        Composite rootComposite = null;
        for (Composite c = composite; c != null; c = c.getParent()) {
            if (c.getParent() instanceof CTabFolder) {
                tabFolder = (CTabFolder) c.getParent();
                rootComposite = c;
                break;
            }
        }
        if (tabFolder == null) {
            return;
        }
        tabFolder.setBorderVisible(false);

        Color bgColor = null;
        if (editor instanceof IDataSourceContainerProvider) {
            DBPDataSourceContainer container = ((IDataSourceContainerProvider) editor).getDataSourceContainer();
            if (container != null) {
                bgColor = UIUtils.getConnectionColor(container.getConnectionConfiguration());
            }
        } else if (editor instanceof DBPContextProvider) {
            DBCExecutionContext context = ((DBPContextProvider) editor).getExecutionContext();
            if (context != null) {
                bgColor = UIUtils.getConnectionColor(context.getDataSource().getContainer().getConnectionConfiguration());
            }
        }
        if (bgColor == null) {
            rootComposite.setBackground(null);
        } else {
            rootComposite.setBackground(bgColor);
        }
    }

    public static void contributeStandardEditorActions(IWorkbenchSite workbenchSite, IContributionManager contributionManager)
    {
        contributionManager.add(ActionUtils.makeCommandContribution(
            workbenchSite,
            IWorkbenchCommandConstants.FILE_SAVE,
            null,
            UIIcon.SAVE,
            null,
            true));
        contributionManager.add(ActionUtils.makeCommandContribution(
            workbenchSite,
            IWorkbenchCommandConstants.FILE_REVERT,
            null,
            UIIcon.RESET,
            null,
            true));
    }

}
